package com.moyin.tts.service;

import java.util.List;

import com.moyin.tts.domain.MoyinLanguage;


public interface IMoyinLanguageService {

    MoyinLanguage selectById(Long id);

    List<MoyinLanguage> selectList(MoyinLanguage moyinCategory);

    int insert(MoyinLanguage moyinCategory);

    int updateById(MoyinLanguage moyinCategory);

    int deleteByIds(Long[] ids);

    int deleteById(Long id);
}
