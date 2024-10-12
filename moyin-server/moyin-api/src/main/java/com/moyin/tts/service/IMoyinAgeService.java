package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinAge;

import java.util.List;


public interface IMoyinAgeService {

    MoyinAge selectById(Long id);

    List<MoyinAge> selectList(MoyinAge category);

    int insert(MoyinAge category);

    int updateById(MoyinAge category);

    int deleteByIds(Long[] ids);

    int deleteById(Long id);
}
