package com.moyin.tts.mapper;

import java.util.List;

import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.domain.MoyinDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 配音员分类Mapper接口
 *
 * @author moyin
 * @date 2024-06-24
 */
@Mapper
public interface MoyinCategoryMapper {
    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    MoyinCategory selectMoyinCategoryById(Long id);

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类集合
     */
    List<MoyinCategory> selectMoyinCategoryList(MoyinCategory moyinCategory);

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    int insertMoyinCategory(MoyinCategory moyinCategory);

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    int updateMoyinCategory(MoyinCategory moyinCategory);

    /**
     * 删除配音员分类
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    int deleteMoyinCategoryById(Long id);

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMoyinCategoryByIds(Long[] ids);
}
