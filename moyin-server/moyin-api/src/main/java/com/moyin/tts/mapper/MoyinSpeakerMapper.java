package com.moyin.tts.mapper;

import java.util.List;
import java.util.Set;

import com.moyin.tts.domain.MoyinSpeaker;
import com.moyin.tts.model.dto.SpeakerDataDTO;
import com.moyin.tts.model.vo.MoyinSpeakerEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerJustShowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 配音员Mapper接口
 *
 * @author moyin
 * @date 2024-06-25
 */
@Mapper
public interface MoyinSpeakerMapper {
    /**
     * 查询配音员
     *
     * @param id 配音员主键
     * @return 配音员
     */
    MoyinSpeaker selectById(Long id);

    /**
     * 查询配音员列表
     *
     * @param speaker 配音员
     * @return 配音员集合
     */
    List<MoyinSpeaker> selectList(MoyinSpeaker speaker);

    /**
     * 新增配音员
     *
     * @param speaker 配音员
     * @return 结果
     */
    int insert(MoyinSpeaker speaker);

    /**
     * 修改配音员
     *
     * @param speaker 配音员
     * @return 结果
     */
    int updateById(MoyinSpeaker speaker);

    /**
     * 删除配音员
     *
     * @param id 配音员主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除配音员
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    Set<Long> selectEmotionSet(Long speakerId);

    Set<Long> selectDomainSet(Long speakerId);

    Set<Long> selectLanguageSet(Long speakerId);

    int insertDomain(@Param("domainId") Long domainId, @Param("speakerId") Long speakerId);

    int insertLanguage(@Param("languageId") Long languageId, @Param("speakerId") Long speakerId);

    int insertEmotion(@Param("emotionId") Long emotionId, @Param("speakerId") Long speakerId);

    int deleteDomain(@Param("domainId") Long domainId, @Param("speakerId") Long speakerId);

    int deleteLanguage(@Param("languageId") Long languageId, @Param("speakerId") Long speakerId);

    int deleteEmotion(@Param("emotionId") Long emotionId, @Param("speakerId") Long speakerId);

    int deleteAllEmotion(@Param("speakerId") Long speakerId);

    int deleteAllLanguage(@Param("speakerId") Long speakerId);

    int deleteAllDomain(@Param("speakerId") Long speakerId);

    String selectEmotionName(Long id);

    String selectDomainName(Long id);

    String selectLanguageName(Long id);

    int insertAliasById(@Param("speakerId") Long speakerId, @Param("userId") Long userId, @Param("alias") String alias);

    int updateAliasById(@Param("speakerId") Long speakerId, @Param("userId") Long userId, @Param("alias") String alias);

    String selectAliasById(@Param("speakerId") Long speakerId, @Param("userId") Long userId);

    String selectAliasName(@Param("speakerId") Long speakerId, @Param("userId") Long userId);

    List<MoyinSpeakerJustShowVo> selectListJustShow(MoyinSpeaker speaker, @Param("userId") Long userId);

    List<MoyinSpeakerJustShowVo> selectSpeakerListBySpeakerDataDTO(SpeakerDataDTO speakerDataDTO);

    List<MoyinSpeakerJustShowVo> getCollectList(@Param("userId") Long userId);

    List<MoyinSpeakerEmotionVo> getEmotionDataList(Long id);

    int selectIsStar(@Param("speakerId") Long id, @Param("userId") Long userId);
}
