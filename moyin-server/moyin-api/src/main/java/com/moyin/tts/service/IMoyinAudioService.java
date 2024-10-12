package com.moyin.tts.service;

/**
 * @author 31734
 */
public interface IMoyinAudioService {

    /**
     * 设置音频数据到Redis
     *
     * @param key        key
     * @param audioBytes 音频数据
     */
    void saveAudioData(String key, byte[] audioBytes);

    /**
     * 从Redis获取音频数据
     *
     * @param key 音频的URL
     * @return 音频数据
     */
    byte[] getAudioData(String key);


}
