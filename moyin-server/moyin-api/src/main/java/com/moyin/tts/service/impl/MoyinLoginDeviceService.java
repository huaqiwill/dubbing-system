package com.moyin.tts.service.impl;

import com.moyin.common.core.domain.entity.SysUser;
import com.moyin.common.core.redis.RedisCache;
import com.moyin.system.mapper.SysUserMapper;
import com.moyin.tts.domain.MoyinLoginDevice;
import com.moyin.tts.mapper.MoyinLoginDeviceMapper;
import com.moyin.tts.service.IMoyinLoginDeviceService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
public class MoyinLoginDeviceService implements IMoyinLoginDeviceService {

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private MoyinLoginDeviceMapper loginDeviceMapper;

    public boolean checkUserAgent(Long userId, String userAgent) {
        MoyinLoginDevice loginDevice = loginDeviceMapper.selectByUserId(userId);
        return loginDevice == null || userAgent.equals(loginDevice.getUserAgent());
    }

    public int add(Long userId, String ua, String ip) {
        MoyinLoginDevice loginDevice = loginDeviceMapper.selectByUserId(userId);
        if (loginDevice != null) {
            return 0;
        }

        SysUser user = userMapper.selectUserById(userId);

        UserAgent userAgent = UserAgent.parseUserAgentString(ua);

        // 获取浏览器信息
        Browser browser = userAgent.getBrowser();

        // 获取操作系统信息
        OperatingSystem os = userAgent.getOperatingSystem();

        // 获取设备类型信息
        DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();

        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();

        // 将LocalDateTime转换为Date
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        loginDeviceMapper.insert(MoyinLoginDevice.builder()
                .ip(ip)
                .userName(user.getUserName())
                .userAgent(ua)
                .userId(userId)
                .browser(browser.getName())
                .os(os.getName())
                .device(deviceType.getName())
                .loginTime(date)
                .online(true)
                .build());

        return 1;
    }


    @Override
    public List<MoyinLoginDevice> list(MoyinLoginDevice loginDevice) {
        return loginDeviceMapper.selectList(loginDevice);
    }

    @Override
    public MoyinLoginDevice info(Long id) {
        return loginDeviceMapper.selectById(id);
    }

    @Override
    public int remove(Long id) {
        return loginDeviceMapper.delete(id);
    }
}
