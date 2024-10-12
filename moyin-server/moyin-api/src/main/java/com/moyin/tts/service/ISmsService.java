package com.moyin.tts.service;

import java.util.Map;

/**
 * 短信服务
 *
 * @author 31734
 */
public interface ISmsService {
    /**
     * 发送验证码
     *
     * @param param
     * @param phone 手机号
     * @return 发送结果
     */
    boolean send(Map<String, Object> param, String phone);
}


