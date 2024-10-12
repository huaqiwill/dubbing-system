package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinTag;
import com.moyin.tts.mapper.MoyinTagMapper;
import com.moyin.tts.service.IMoyinTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 配音员分类Service业务层处理
 *
 * @author moyin
 * @date 2024-06-24
 */
@Service
public class MoyinTagServiceImpl implements IMoyinTagService {

    @Resource
    private MoyinTagMapper tagMapper;

    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    @Override
    public MoyinTag selectById(Long id) {
        return tagMapper.selectTagById(id);
    }

    /**
     * 查询配音员分类列表
     *
     * @param tag 配音员分类
     * @return 配音员分类
     */
    @Override
    public List<MoyinTag> selectList(MoyinTag tag) {
        return tagMapper.selectTagList(tag);
    }

    /**
     * 新增配音员分类
     *
     * @param tag 配音员分类
     * @return 结果
     */
    @Override
    public int insert(MoyinTag tag) {
        tag.setCreateTime(DateUtils.getNowDate());
        return tagMapper.insertTag(tag);
    }

    /**
     * 修改配音员分类
     *
     * @param tag 配音员分类
     * @return 结果
     */
    @Override
    public int updateById(MoyinTag tag) {
        tag.setUpdateTime(DateUtils.getNowDate());
        return tagMapper.updateTagById(tag);
    }

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteByIds(Long[] ids) {
        return tagMapper.deleteTagByIds(ids);
    }

    /**
     * 删除配音员分类信息
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteMoyinCategoryById(Long id) {
        return tagMapper.deleteTagById(id);
    }
}
