package com.moyin.tts.service;

import java.util.List;

import com.moyin.tts.domain.MoyinSpeaker;
import com.moyin.tts.model.dto.SpeakerDataDTO;
import com.moyin.tts.model.vo.MoyinSpeakerJustShowVo;


public interface IMoyinSpeakerService {

    int insert(MoyinSpeaker speaker);

    //    @CacheEvict(value = "myCache", key = "#id")
    int deleteById(Long id);

    int deleteByIds(Long[] ids);

    int updateById(MoyinSpeaker speaker);

    MoyinSpeaker selectById(Long id);

    void setSpeakerAlias(MoyinSpeaker speaker);

    //    @Cacheable(value = "myCache",key = "#speaker.id")
    List<MoyinSpeaker> selectList(MoyinSpeaker speaker);

    int insertEmotion(Long emotionId, Long speakerId);

    int insertLanguage(Long languageId, Long speakerId);

    int insertDomain(Long domainId, Long speakerId);

    int deleteEmotion(Long emotionId, Long speakerId);

    int deleteLanguage(Long languageId, Long speakerId);

    int deleteDomain(Long domainId, Long speakerId);

    int updateAliasById(MoyinSpeaker speaker);

    List<MoyinSpeaker> recentList(Long userId);

    List<MoyinSpeakerJustShowVo> selectListJustShow(MoyinSpeaker speaker, Long userId);

    void recentAdd(MoyinSpeaker speaker, Long userId);

    List<MoyinSpeaker> selectListAll(MoyinSpeaker speaker);

    List<MoyinSpeakerJustShowVo> selectSpeakerListBySpeakerDataDTO(SpeakerDataDTO speakerDataDTO);

    List<MoyinSpeakerJustShowVo> getCollectList(Long userId);
}
