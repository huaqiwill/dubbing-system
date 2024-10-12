package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.domain.MoyinStyle;
import com.moyin.tts.mapper.MoyinStyleMapper;
import com.moyin.tts.service.IMoyinStyleService;
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
public class MoyinStyleServiceImpl implements IMoyinStyleService {

    @Resource
    private MoyinStyleMapper styleMapper;

    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    @Override
    public MoyinStyle selectById(Long id) {
        return styleMapper.selectStyleById(id);
    }

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类
     */
    @Override
    public List<MoyinStyle> selectList(MoyinStyle moyinCategory) {
        return styleMapper.selectStyleList(moyinCategory);
    }

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    @Override
    public int insert(MoyinStyle moyinCategory) {
        moyinCategory.setCreateTime(DateUtils.getNowDate());
        return styleMapper.insertStyle(moyinCategory);
    }

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    @Override
    public int updateById(MoyinStyle moyinCategory) {
        moyinCategory.setUpdateTime(DateUtils.getNowDate());
        return styleMapper.updateStyle(moyinCategory);
    }

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteByIds(Long[] ids) {
        return styleMapper.deleteStyleByIds(ids);
    }

    /**
     * 删除配音员分类信息
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteStyleById(Long id) {
        return styleMapper.deleteStyleById(id);
    }
}
