package com.moyin.framework.web.utils;


import com.moyin.common.core.redis.RedisCache;
import com.moyin.common.utils.SecurityUtils;

import javax.annotation.Resource;

public class UserAgentUtils {

    private RedisCache redisCache;

    private final String key;

    public UserAgentUtils(RedisCache redisCache, Long userId) {
        key = "device:" + userId;
        this.redisCache = redisCache;
    }

    public void addUserDevice(String device) {
        redisCache.setCacheObject(key, device);
    }

    public boolean removeUserDevice() {
        return redisCache.deleteObject(key);
    }

    public boolean hasUserDevice() {
        return redisCache.hasKey(key);
    }

    public String getUserDevice() {
        return redisCache.getCacheObject(key);
    }
}

