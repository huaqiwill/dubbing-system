package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinNotice;

import java.util.List;

public interface IMoyinNoticeService {

    List<MoyinNotice> selectList(MoyinNotice moyinCategory);

    MoyinNotice selectById(Long id);

    int insert(MoyinNotice moyinCategory);

    int updateById(MoyinNotice moyinCategory);

    int deleteByIds(Long[] ids);

    int deleteById(Long id);
}
