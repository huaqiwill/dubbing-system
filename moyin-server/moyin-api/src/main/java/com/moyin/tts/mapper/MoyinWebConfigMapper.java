package com.moyin.tts.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31734
 */
@Mapper
public interface MoyinWebConfigMapper {

    String getUserProtocol();

    int setUserProtocol(String protocol);
}
