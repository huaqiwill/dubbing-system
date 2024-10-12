package com.moyin.tts.task;

import com.moyin.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CacheCleanupTask {

    @Resource
    private RedisCache redisCache;

    // 每天凌晨0点执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearCache() {
        // 获取所有符合前缀的键
        Set<String> keys = new HashSet<>(redisCache.keys("tts-limit*"));
        if (!keys.isEmpty()) {
            // 批量删除这些键
            redisCache.deleteObject(keys);
        }
    }
}
