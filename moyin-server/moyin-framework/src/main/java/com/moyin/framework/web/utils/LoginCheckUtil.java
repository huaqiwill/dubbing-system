package com.moyin.framework.web.utils;

import com.moyin.common.core.redis.RedisCache;

import javax.servlet.http.HttpServletRequest;

public class LoginCheckUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // 只允许：一台客户端、一台设备登录
    public static boolean checkClient(RedisCache redisCache, Long userId, String userAgent) {
        UserAgentUtils userAgentUtils = new UserAgentUtils(redisCache, userId);
        if (!userAgentUtils.hasUserDevice()) {
            userAgentUtils.addUserDevice(userAgent);
            return true;
        }

        return userAgentUtils.getUserDevice().equals(userAgent);
    }

}
