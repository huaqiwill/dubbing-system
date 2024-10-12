package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinAge;
import com.moyin.tts.domain.MoyinDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MoyinAgeMapper {

    public MoyinAge selectAgeById(Long id);

    public List<MoyinAge> selectAgeList(MoyinAge moyinCategory);

    public int insertAge(MoyinAge moyinCategory);

    public int updateAge(MoyinAge moyinCategory);

    public int deleteAgeById(Long id);

    public int deleteAgeByIds(Long[] ids);
}
