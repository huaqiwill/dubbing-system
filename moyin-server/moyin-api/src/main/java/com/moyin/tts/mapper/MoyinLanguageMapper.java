package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.domain.MoyinLanguage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MoyinLanguageMapper {

    MoyinLanguage selectById(Long id);

    List<MoyinLanguage> selectList(MoyinLanguage language);

    int insert(MoyinLanguage language);

    int update(MoyinLanguage language);

    int deleteById(Long id);

    int deleteByIds(Long[] ids);
}
