package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.model.vo.MoyinDictVo;
import com.moyin.tts.model.vo.MoyinEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 配音员分类Mapper接口
 *
 * @author moyin
 * @date 2024-06-24
 */
@Mapper
public interface MoyinDictMapper {
    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    public MoyinDict selectMoyinCategoryById(Long id);

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类集合
     */
    public List<MoyinDict> selectMoyinCategoryList(MoyinDict moyinCategory);

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int insertMoyinCategory(MoyinDict moyinCategory);

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int updateMoyinCategory(MoyinDict moyinCategory);

    /**
     * 删除配音员分类
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    public int deleteMoyinCategoryById(Long id);

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMoyinCategoryByIds(Long[] ids);

    List<MoyinDictVo> userNameList();

    List<MoyinDictVo> speakerNameList();

    List<MoyinDictVo> lexiconNameList();

    List<MoyinDictVo> domainNameList();

    List<MoyinDictVo> languageNameList();

    List<MoyinDictVo> emotionNameList();

    List<MoyinEmotionVo> emotionList();

    List<MoyinSpeakerEmotionVo> getSpeakerEmotionList(Long speakerId);

    List<MoyinSpeakerVo> speakerList();
}
