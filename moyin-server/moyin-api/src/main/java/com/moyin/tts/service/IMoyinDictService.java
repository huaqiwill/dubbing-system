package com.moyin.tts.service;

import java.util.List;

import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.model.vo.MoyinDictVo;
import com.moyin.tts.model.vo.MoyinEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerVo;

/**
 * 配音员分类Service接口
 *
 * @author moyin
 * @date 2024-06-24
 */
public interface IMoyinDictService {
    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    public MoyinDict selectById(Long id);

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类集合
     */
    public List<MoyinDict> selectList(MoyinDict moyinCategory);

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int insert(MoyinDict moyinCategory);

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int updateById(MoyinDict moyinCategory);

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的配音员分类主键集合
     * @return 结果
     */
    public int deleteByIds(Long[] ids);

    /**
     * 删除配音员分类信息
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    public int deleteMoyinCategoryById(Long id);

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
