package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 魔音会员管理
 *
 * @author 31734
 */
@Mapper
public interface MoyinMemberMapper {

    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    int insert(MoyinMember member);

    /**
     * 通过ID更新
     *
     * @param member
     * @return
     */
    int updateById(MoyinMember member);

    /**
     * 通过ID删除会员
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 通过ID批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 查询列表
     *
     * @param moyinCategory
     * @return
     */
    List<MoyinMember> selectList(MoyinMember moyinCategory);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    String selectById(Long id);
}
