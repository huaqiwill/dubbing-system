package com.moyin.tts.service.impl;

import java.util.List;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.mapper.MoyinDictMapper;
import com.moyin.tts.model.vo.MoyinDictVo;
import com.moyin.tts.model.vo.MoyinEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerVo;
import org.springframework.stereotype.Service;
import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.service.IMoyinDictService;

import javax.annotation.Resource;

/**
 * 配音员分类Service业务层处理
 *
 * @author moyin
 * @date 2024-06-24
 */
@Service
public class MoyinDictServiceImpl implements IMoyinDictService {

    @Resource
    private MoyinDictMapper dictMapper;

    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    @Override
    public MoyinDict selectById(Long id) {
        return dictMapper.selectMoyinCategoryById(id);
    }

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类
     */
    @Override
    public List<MoyinDict> selectList(MoyinDict moyinCategory) {
        return dictMapper.selectMoyinCategoryList(moyinCategory);
    }

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    @Override
    public int insert(MoyinDict moyinCategory) {
        moyinCategory.setCreateTime(DateUtils.getNowDate());
        return dictMapper.insertMoyinCategory(moyinCategory);
    }

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    @Override
    public int updateById(MoyinDict moyinCategory) {
        moyinCategory.setUpdateTime(DateUtils.getNowDate());
        return dictMapper.updateMoyinCategory(moyinCategory);
    }

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteByIds(Long[] ids) {
        return dictMapper.deleteMoyinCategoryByIds(ids);
    }

    /**
     * 删除配音员分类信息
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteMoyinCategoryById(Long id) {
        return dictMapper.deleteMoyinCategoryById(id);
    }

    @Override
    public List<MoyinDictVo> userNameList() {
        return dictMapper.userNameList();
    }

    @Override
    public List<MoyinDictVo> speakerNameList() {
        return dictMapper.speakerNameList();
    }

    @Override
    public List<MoyinDictVo> lexiconNameList() {
        return dictMapper.lexiconNameList();
    }

    @Override
    public List<MoyinDictVo> domainNameList() {
        return dictMapper.domainNameList();
    }

    @Override
    public List<MoyinDictVo> languageNameList() {
        return dictMapper.languageNameList();
    }

    @Override
    public List<MoyinDictVo> emotionNameList() {
        return dictMapper.emotionNameList();
    }

    @Override
    public List<MoyinEmotionVo> emotionList() {
        return dictMapper.emotionList();
    }

    @Override
    public List<MoyinSpeakerEmotionVo> getSpeakerEmotionList(Long speakerId) {
        return dictMapper.getSpeakerEmotionList(speakerId);
    }

    @Override
    public List<MoyinSpeakerVo> speakerList() {
        return dictMapper.speakerList();
    }
}
