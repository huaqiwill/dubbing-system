package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.domain.MoyinStyle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoyinStyleMapper {

    public MoyinStyle selectStyleById(Long id);

    public List<MoyinStyle> selectStyleList(MoyinStyle moyinCategory);

    public int insertStyle(MoyinStyle moyinCategory);


    public int updateStyle(MoyinStyle moyinCategory);


    public int deleteStyleById(Long id);

    public int deleteStyleByIds(Long[] ids);
}
