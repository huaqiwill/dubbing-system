package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinMember;
import com.moyin.tts.domain.MoyinNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 魔音公告管理
 *
 * @author 31734
 */
@Mapper
public interface MoyinNoticeMapper {

    /**
     * @param member
     * @return
     */
    int insert(MoyinNotice member);

    /**
     * @param member
     * @return
     */
    int updateById(MoyinNotice member);

    /**
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * @param id
     * @return
     */
    MoyinNotice selectById(Long id);

    /**
     * @param moyinCategory
     * @return
     */
    List<MoyinNotice> selectList(MoyinNotice moyinCategory);

    /**
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);
}
