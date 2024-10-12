package com.moyin.tts.service.impl;

import com.moyin.tts.mapper.MoyinWebConfigMapper;
import com.moyin.tts.service.IMoyinSiteConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MoyinSiteConfigServiceImpl implements IMoyinSiteConfigService {

    @Resource
    private MoyinWebConfigMapper webConfigMapper;

    @Override
    public int getWebConfig() {
        return 0;
    }

    @Override
    public String getUserProtocol() {
        return webConfigMapper.getUserProtocol();
    }

    @Override
    public int setUserProtocol(String protocol) {
        return webConfigMapper.setUserProtocol(protocol);
    }
}
