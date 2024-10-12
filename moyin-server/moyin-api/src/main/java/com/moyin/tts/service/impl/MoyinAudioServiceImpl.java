package com.moyin.tts.service.impl;

import com.moyin.common.core.redis.RedisCache;
import com.moyin.tts.service.IMoyinAudioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * @author 31734
 */
@Service
public class MoyinAudioServiceImpl implements IMoyinAudioService {

//    @Resource
//    private RedisTemplate<String, MoyinTtsResultVo> redisTemplate;

    @Resource
    private RedisCache redisCache;

    private final String prefix = "audio:";

    /**
     * 存储音频数据到Redis
     *
     * @param key        键
     * @param audioBytes 音频数据字节数组
     */
    @Override
    public void saveAudioData(String key, byte[] audioBytes) {
//        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(byte[].class));
        // Base64编码
        String encodedString = Base64.getEncoder().encodeToString(audioBytes);
        redisCache.setCacheObject(prefix + key, encodedString);
//        redisTemplate.opsForValue().set(prefix + key, ttsResultVo);
//        redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer<>(byte[].class));
    }

    /**
     * 从Redis读取音频数据
     *
     * @param key 键
     * @return 音频数据字节数组
     */
    @Override
    public byte[] getAudioData(String key) {
//        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(byte[].class));
//        return redisTemplate.opsForValue().get(prefix + key);
        // Bas64解码
        String encodedString = redisCache.getCacheObject(prefix + key);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        //
        return decodedBytes;
    }
}
