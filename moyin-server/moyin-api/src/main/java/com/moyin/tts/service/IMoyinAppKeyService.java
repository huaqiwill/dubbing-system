package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinAppKey;

import java.util.List;

public interface IMoyinAppKeyService {
    MoyinAppKey findAppKeyById(Long id);

    List<MoyinAppKey> list(MoyinAppKey appKey);

    int updateById(MoyinAppKey moyinAppKey);

    int insert(MoyinAppKey appKey);

    int deleteById(Long id);

    MoyinAppKey getCurrentAppKey();
}
