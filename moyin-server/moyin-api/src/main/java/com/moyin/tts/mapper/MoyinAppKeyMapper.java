package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinAppKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 31734
 */
@Mapper
public interface MoyinAppKeyMapper {

    /**
     * 插入秘钥配置
     *
     * @param appKey
     * @return
     */
    int insertAppKey(MoyinAppKey appKey);

    /**
     * 通过id查询秘钥配置
     *
     * @param id
     * @return
     */
    MoyinAppKey findAppKeyById(Long id);

    int updateAppKeyById(MoyinAppKey appKey);

    int deleteAppKeyById(Long id);

    List<MoyinAppKey> appKeyList(MoyinAppKey appKey);

    int disableAllAppKey();

    MoyinAppKey getCurrentAppKey();
}
