package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinAge;

import java.util.List;


public interface IMoyinGenderService {

    MoyinAge selectById(Long id);

    /**
     * 查询配音员分类列表
     *
     * @param category 配音员分类
     * @return 配音员分类集合
     */
    List<MoyinAge> selectList(MoyinAge category);

    /**
     * 新增配音员分类
     *
     * @param category 配音员分类
     * @return 结果
     */
    int insert(MoyinAge category);

    /**
     * 修改配音员分类
     *
     * @param category 配音员分类
     * @return 结果
     */
    int updateById(MoyinAge category);

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的配音员分类主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除配音员分类信息
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    int deleteAgeById(Long id);
}
