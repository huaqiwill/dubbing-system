package com.moyin.tts.service.impl;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

import com.alipay.api.domain.UserDetails;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.redis.RedisCache;
import com.moyin.common.utils.SecurityUtils;
import com.moyin.tts.domain.MoyinMemberRecentSpeaker;
import com.moyin.tts.model.dto.SpeakerDataDTO;
import com.moyin.tts.model.vo.MoyinSpeakerJustShowVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.moyin.tts.mapper.MoyinSpeakerMapper;
import com.moyin.tts.domain.MoyinSpeaker;
import com.moyin.tts.service.IMoyinSpeakerService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class MoyinSpeakerServiceImpl implements IMoyinSpeakerService {

    @Resource
    private MoyinSpeakerMapper speakerMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    @Transient
    public int deleteByIds(Long[] ids) {
        for (Long id : ids) {
            speakerMapper.deleteAllLanguage(id);
            speakerMapper.deleteAllDomain(id);
            speakerMapper.deleteAllEmotion(id);
        }
        return speakerMapper.deleteByIds(ids);
    }


    @Override
    @Transactional
    public int insert(MoyinSpeaker speaker) {
        int result = speakerMapper.insert(speaker);

        if (speaker.getDomainIdSet() != null) {
            speaker.getDomainIdSet().forEach(domainId -> {
                speakerMapper.insertDomain(domainId, speaker.getId());
            });
        }
        if (speaker.getEmotionIdSet() != null) {
            speaker.getEmotionIdSet().forEach(emotionId -> {
                speakerMapper.insertEmotion(emotionId, speaker.getId());
            });
        }
        if (speaker.getLanguageIdSet() != null) {
            speaker.getLanguageIdSet().forEach(languageId -> {
                speakerMapper.insertLanguage(languageId, speaker.getId());
            });
        }

        return result;
    }

    @Override
    @Transient
    public int deleteById(Long id) {
        speakerMapper.deleteAllDomain(id);
        speakerMapper.deleteAllEmotion(id);
        speakerMapper.deleteAllLanguage(id);
        return speakerMapper.deleteById(id);
    }

    private void updateSpeakerEmotion(MoyinSpeaker speaker) {
        Set<Long> emotionIdSet = speakerMapper.selectEmotionSet(speaker.getId());
//        emotionIdSet = emotionIdSet.stream().filter(item -> item != 0).collect(Collectors.toSet());

        // 1.移除删除的
        Set<Long> needRemove = new HashSet<>(emotionIdSet);
        needRemove.removeAll(speaker.getEmotionIdSet());
        needRemove.forEach(emotionId -> {
            if (emotionId != 0) {
                speakerMapper.deleteEmotion(emotionId, speaker.getId());
            }
        });
        // 2.添加新增的
        Set<Long> needAdd = new HashSet<>(speaker.getEmotionIdSet());
        needAdd.removeAll(emotionIdSet);
        needAdd.forEach(emotionId -> {
            if (emotionId != 0) {
                speakerMapper.insertEmotion(emotionId, speaker.getId());
            }
        });
    }

    private void updateSpeakerLanguage(MoyinSpeaker speaker) {
        Set<Long> languageIdSet = speakerMapper.selectLanguageSet(speaker.getId());

        // 1.移除删除的
        Set<Long> needRemove = new HashSet<>(languageIdSet);
        needRemove.removeAll(speaker.getLanguageIdSet());
        needRemove.forEach(languageId -> {
            speakerMapper.deleteLanguage(languageId, speaker.getId());
        });
        // 2.添加新增的
        Set<Long> needAdd = new HashSet<>(speaker.getLanguageIdSet());
        needAdd.removeAll(languageIdSet);
        needAdd.forEach(languageId -> {
            speakerMapper.insertLanguage(languageId, speaker.getId());
        });
    }

    private void updateSpeakerDomain(MoyinSpeaker speaker) {
        Set<Long> domainIdSet = speakerMapper.selectDomainSet(speaker.getId());
        System.out.println(Arrays.toString(domainIdSet.toArray()));
        // 1.移除删除的
        Set<Long> needRemove = new HashSet<>(domainIdSet);
        needRemove.removeAll(speaker.getDomainIdSet());
        needRemove.forEach(domainId -> {
            speakerMapper.deleteDomain(domainId, speaker.getId());
        });
        System.out.println(Arrays.toString(needRemove.toArray()));
        // 2.添加新增的
        Set<Long> needAdd = new HashSet<>(speaker.getDomainIdSet());
        needAdd.removeAll(domainIdSet);
        System.out.println(Arrays.toString(needAdd.toArray()));
        needAdd.forEach(domainId -> {
            speakerMapper.insertDomain(domainId, speaker.getId());
        });
    }


    @Override
    @Transient
    public int updateById(MoyinSpeaker speaker) {
        redisCache.deleteObject("speaker");
        updateSpeakerEmotion(speaker);
        updateSpeakerLanguage(speaker);
        updateSpeakerDomain(speaker);
        return speakerMapper.updateById(speaker);
    }

    @Override
    public MoyinSpeaker selectById(Long id) {
//        MoyinSpeaker speaker = redisCache.getCacheMapValue("speaker", id.toString());
//        if (speaker != null) {
//            return speaker;
//        }
        MoyinSpeaker speaker = speakerMapper.selectById(id);
        setSpeakerFunc(speaker);
//        redisCache.setCacheMapValue("speaker", speaker.getId().toString(), speaker);
        return speaker;
    }

    public void setSpeakerAlias(MoyinSpeaker speaker) {
        Long userId = SecurityUtils.getUserId();
        Long speakerId = speaker.getId();
        speaker.setAlias(speakerMapper.selectAliasById(speakerId, userId));
    }

    private void setSpeakerFunc(MoyinSpeaker speaker) {
        Long userId = SecurityUtils.getUserId();

        speaker.setEmotionIdSet(speakerMapper.selectEmotionSet(speaker.getId()));
        speaker.setEmotion(speakerMapper.selectEmotionName(speaker.getId()));
        speaker.setDomainIdSet(speakerMapper.selectDomainSet(speaker.getId()));
        speaker.setDomain(speakerMapper.selectDomainName(speaker.getId()));
        speaker.setLanguageIdSet(speakerMapper.selectLanguageSet(speaker.getId()));
        speaker.setLanguage(speakerMapper.selectLanguageName(speaker.getId()));
        speaker.setEmotionDataList(speakerMapper.getEmotionDataList(speaker.getId()));
        speaker.setAlias(speakerMapper.selectAliasById(speaker.getId(), userId));
        speaker.setIsStar(speakerMapper.selectIsStar(speaker.getId(), userId) > 0);
    }

    @Override
    public List<MoyinSpeaker> selectList(MoyinSpeaker moyinSpeaker) {
        // 改写一下
        // 首先查询缓存，从数据库查询ID集合，如果存在

        log.warn("魔音 {}", moyinSpeaker);

        List<MoyinSpeaker> moyinSpeakers = speakerMapper.selectList(moyinSpeaker);
        moyinSpeakers.forEach(this::setSpeakerFunc);

        if (moyinSpeaker.getDomainIdSet() == null
                || moyinSpeaker.getEmotionIdSet() == null
                || moyinSpeaker.getLanguageIdSet() == null) {
            return moyinSpeakers;
        }

        if (moyinSpeaker.getDomainIdSet().isEmpty()
                || moyinSpeaker.getEmotionIdSet().isEmpty()
                || moyinSpeaker.getLanguageIdSet().isEmpty()) {
            return moyinSpeakers;
        }

        // 筛选出符合domain、language、emotion

        return moyinSpeakers
                .stream()
                .filter(speaker -> {

                    Set<Long> domainSet = speakerMapper.selectDomainSet(speaker.getId());
                    Set<Long> languageSet = speakerMapper.selectLanguageSet(speaker.getId());
                    Set<Long> emotionIdSet = speakerMapper.selectEmotionSet(speaker.getId());

                    log.warn("领域： {},{}", Arrays.toString(domainSet.toArray()), Arrays.toString(moyinSpeaker.getDomainIdSet().toArray()));
                    log.warn("语言： {},{}", Arrays.toString(languageSet.toArray()), Arrays.toString(moyinSpeaker.getLanguageIdSet().toArray()));
                    log.warn("情绪： {},{}", Arrays.toString(emotionIdSet.toArray()), Arrays.toString(moyinSpeaker.getEmotionIdSet().toArray()));
                    log.warn("筛选结果：{}", domainSet.containsAll(moyinSpeaker.getDomainIdSet())
                            && languageSet.containsAll(moyinSpeaker.getLanguageIdSet())
                            && emotionIdSet.containsAll(moyinSpeaker.getEmotionIdSet()));

                    return domainSet.containsAll(moyinSpeaker.getDomainIdSet())
                            && languageSet.containsAll(moyinSpeaker.getLanguageIdSet())
                            && emotionIdSet.containsAll(moyinSpeaker.getEmotionIdSet());
                })
                .collect(Collectors.toList());

//        return moyinSpeakers;
    }

    @Override
    public int insertEmotion(Long emotionId, Long speakerId) {
        return speakerMapper.insertEmotion(emotionId, speakerId);
    }

    @Override
    public int insertLanguage(Long languageId, Long speakerId) {
        return speakerMapper.insertLanguage(languageId, speakerId);
    }

    @Override
    public int insertDomain(Long domainId, Long speakerId) {
        return speakerMapper.insertDomain(domainId, speakerId);
    }

    @Override
    public int deleteEmotion(Long emotionId, Long speakerId) {
        return speakerMapper.deleteEmotion(emotionId, speakerId);
    }

    @Override
    public int deleteLanguage(Long languageId, Long speakerId) {
        return speakerMapper.deleteLanguage(languageId, speakerId);
    }

    @Override
    public int deleteDomain(Long domainId, Long speakerId) {
        return speakerMapper.deleteDomain(domainId, speakerId);
    }

    @Override
    public int updateAliasById(MoyinSpeaker speaker) {
        Long userId = SecurityUtils.getUserId();
        Long speakerId = speaker.getId();
        int result;
        if (speakerMapper.selectAliasById(speakerId, userId) != null) {
            result = speakerMapper.updateAliasById(speakerId, userId, speaker.getAlias());
        } else {
            result = speakerMapper.insertAliasById(speakerId, userId, speaker.getAlias());
        }
        return result;
    }

    @Override
    public List<MoyinSpeaker> recentList(Long userId) {
        Map<String, MoyinSpeaker> recentMap = redisCache.getCacheMap("recent:" + userId);
        if (recentMap == null || recentMap.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(recentMap.values());
    }


    @Override
    public List<MoyinSpeakerJustShowVo> selectListJustShow(MoyinSpeaker speaker, Long userId) {
        return speakerMapper.selectListJustShow(speaker, userId);
    }


    @Override
    public List<MoyinSpeaker> selectListAll(MoyinSpeaker speaker) {
        if (redisCache.hasKey("moyinSpeaker")) {
            List<MoyinSpeaker> moyinSpeakers = redisCache.getCacheList("moyinSpeaker");
            return moyinSpeakers;
        }
        List<MoyinSpeaker> speakerList = selectList(speaker);
        redisCache.setCacheList("moyinSpeaker", speakerList);
        return speakerList;
    }

    @Override
    public List<MoyinSpeakerJustShowVo> selectSpeakerListBySpeakerDataDTO(SpeakerDataDTO speakerDataDTO) {
        return speakerMapper.selectSpeakerListBySpeakerDataDTO(speakerDataDTO);
    }

    @Override
    public List<MoyinSpeakerJustShowVo> getCollectList(Long userId) {
        return speakerMapper.getCollectList(userId);
    }

    @Override
    public void recentAdd(MoyinSpeaker speaker, Long userId) {
        MoyinSpeaker speakerCache = redisCache.getCacheMapValue("recent:" + userId, speaker.getId().toString());
        if (speakerCache != null) {
            return;
        }
        redisCache.setCacheMapValue("recent:" + userId, speaker.getId().toString(), speaker);
    }
}
