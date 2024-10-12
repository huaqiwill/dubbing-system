package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinAge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 配音员分类Mapper接口
 *
 * @author moyin
 * @date 2024-06-24
 */
@Mapper
public interface MoyinGenderMapper {
    /**
     * 查询配音员分类
     *
     * @param id 配音员分类主键
     * @return 配音员分类
     */
    public MoyinAge selectAgeById(Long id);

    /**
     * 查询配音员分类列表
     *
     * @param moyinCategory 配音员分类
     * @return 配音员分类集合
     */
    public List<MoyinAge> selectAgeList(MoyinAge moyinCategory);

    /**
     * 新增配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int insertAge(MoyinAge moyinCategory);

    /**
     * 修改配音员分类
     *
     * @param moyinCategory 配音员分类
     * @return 结果
     */
    public int updateAge(MoyinAge moyinCategory);

    /**
     * 删除配音员分类
     *
     * @param id 配音员分类主键
     * @return 结果
     */
    public int deleteAgeById(Long id);

    /**
     * 批量删除配音员分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAgeByIds(Long[] ids);
}
