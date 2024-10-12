package com.moyin.tts.service;

public interface IMoyinSiteConfigService {
    int getWebConfig();

    String getUserProtocol();

    int setUserProtocol(String protocol);
}
