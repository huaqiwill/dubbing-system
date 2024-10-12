package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinCategory;

import java.util.List;


public interface IMoyinCategoryService {

    MoyinCategory selectById(Long id);

    List<MoyinCategory> selectList(MoyinCategory category);

    int insert(MoyinCategory category);

    int updateById(MoyinCategory category);

    int deleteByIds(Long[] ids);

    int deleteAgeById(Long id);
}
