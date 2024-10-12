package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinAppKey;
import com.moyin.tts.mapper.MoyinAppKeyMapper;
import com.moyin.tts.service.IMoyinAppKeyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MoyinAppKeyServiceImpl implements IMoyinAppKeyService {

    @Resource
    private MoyinAppKeyMapper appKeyMapper;

    @Override
    public MoyinAppKey findAppKeyById(Long id) {
        return appKeyMapper.findAppKeyById(id);
    }

    @Override
    public List<MoyinAppKey> list(MoyinAppKey appKey) {
        return appKeyMapper.appKeyList(appKey);
    }

    @Override
    public int updateById(MoyinAppKey moyinAppKey) {
        if (moyinAppKey.getStatus() == 1) {
            appKeyMapper.disableAllAppKey();
        }

        return appKeyMapper.updateAppKeyById(moyinAppKey);
    }

    @Override
    public int insert(MoyinAppKey appKey) {
        return appKeyMapper.insertAppKey(appKey);
    }

    @Override
    public int deleteById(Long id) {
        return appKeyMapper.deleteAppKeyById(id);
    }

    @Override
    public MoyinAppKey getCurrentAppKey() {
        return appKeyMapper.getCurrentAppKey();
    }
}
