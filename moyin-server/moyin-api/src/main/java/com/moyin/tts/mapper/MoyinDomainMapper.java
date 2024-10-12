package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinDomain;
import com.moyin.tts.domain.MoyinEmotion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 配音员情绪Mapper接口
 *
 * @author moyin
 * @date 2024-06-25
 */
@Mapper
public interface MoyinDomainMapper {
    /**
     * 查询配音员情绪
     *
     * @param id 配音员情绪主键
     * @return 配音员情绪
     */
    MoyinDomain selectById(Long id);

    /**
     * 查询配音员情绪列表
     *
     * @param moyinEmotion 配音员情绪
     * @return 配音员情绪集合
     */
    List<MoyinDomain> selectList(MoyinDomain moyinEmotion);

    /**
     * 新增配音员情绪
     *
     * @param moyinEmotion 配音员情绪
     * @return 结果
     */
    int insert(MoyinDomain moyinEmotion);

    /**
     * 修改配音员情绪
     *
     * @param moyinEmotion 配音员情绪
     * @return 结果
     */
    int update(MoyinDomain moyinEmotion);

    /**
     * 删除配音员情绪
     *
     * @param id 配音员情绪主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除配音员情绪
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
