package com.moyin.tts.controller;

import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.redis.RedisCache;
import com.moyin.tts.service.ISmsService;
import com.moyin.tts.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MoyinSmsController extends BaseController {

    @Resource
    private ISmsService smsService;

    @Resource
    private RedisCache redisCache;

    //发送短信验证码
    @GetMapping("/send/{phone}")
    public AjaxResult code(@PathVariable String phone) {
        //1、从redis中获取验证码，如果获取到就直接返回

        String code = redisCache.getCacheObject("phone:" + phone);

        if (!StringUtils.isEmpty(code)) {
            return AjaxResult.error();
        }

        //2、如果获取不到，就进行阿里云发送
        // 生成验证码的随机值
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        //调用方法
        boolean isSend = smsService.send(param, phone);
        if (!isSend) {
            return AjaxResult.error();
        }

        //往redis中设置数据：key、value、过期值、过期时间单位  MINUTES代表分钟
        redisCache.setCacheObject("phone:" + phone, code, 5, TimeUnit.MINUTES);
        return AjaxResult.success();
    }
}
