package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoyinTagMapper {

    MoyinTag selectTagById(Long id);

    List<MoyinTag> selectTagList(MoyinTag moyinCategory);

    int insertTag(MoyinTag moyinCategory);

    int updateTagById(MoyinTag moyinCategory);

    int deleteTagById(Long id);

    int deleteTagByIds(Long[] ids);
}
