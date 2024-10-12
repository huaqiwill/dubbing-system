package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.domain.MoyinTag;

import java.util.List;

/**
 * 配音员分类Service接口
 * 
 * @author moyin
 * @date 2024-06-24
 */
public interface IMoyinTagService
{
    /**
     * 查询配音员分类
     * 
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    public MoyinTag selectById(Long id);

    /**
     * 查询配音员分类列表
     * 
     * @param moyinCategory 配音员分类
     * @return 配音员分类集合
     */
    public List<MoyinTag> selectList(MoyinTag moyinCategory);

    /**
     * 新增配音员分类
     * 
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int insert(MoyinTag moyinCategory);

    /**
     * 修改配音员分类
     * 
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int updateById(MoyinTag moyinCategory);

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
}
