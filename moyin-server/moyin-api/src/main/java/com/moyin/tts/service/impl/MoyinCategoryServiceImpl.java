package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.mapper.MoyinCategoryMapper;
import com.moyin.tts.service.IMoyinCategoryService;
import com.moyin.tts.service.IMoyinDictService;
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
public class MoyinCategoryServiceImpl implements IMoyinCategoryService {

    @Resource
    private MoyinCategoryMapper moyinCategoryMapper;

    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    @Override
    public MoyinCategory selectById(Long id) {
        return moyinCategoryMapper.selectMoyinCategoryById(id);
    }

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类
     */
    @Override
    public List<MoyinCategory> selectList(MoyinCategory moyinCategory) {
        return moyinCategoryMapper.selectMoyinCategoryList(moyinCategory);
    }

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    @Override
    public int insert(MoyinCategory moyinCategory) {
        moyinCategory.setCreateTime(DateUtils.getNowDate());
        return moyinCategoryMapper.insertMoyinCategory(moyinCategory);
    }

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    @Override
    public int updateById(MoyinCategory moyinCategory) {
        moyinCategory.setUpdateTime(DateUtils.getNowDate());
        return moyinCategoryMapper.updateMoyinCategory(moyinCategory);
    }

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteByIds(Long[] ids) {
        return moyinCategoryMapper.deleteMoyinCategoryByIds(ids);
    }

    /**
     * 删除配音员分类信息
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    @Override
    public int deleteAgeById(Long id) {
        return moyinCategoryMapper.deleteMoyinCategoryById(id);
    }
}
