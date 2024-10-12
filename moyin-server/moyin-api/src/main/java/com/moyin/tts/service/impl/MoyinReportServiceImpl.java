package com.moyin.tts.service.impl;

import com.moyin.common.constant.CacheConstants;
import com.moyin.common.core.redis.RedisCache;
import com.moyin.tts.mapper.MoyinReportMapper;
import com.moyin.tts.model.vo.UserTtsVo;
import com.moyin.tts.service.IMoyinReportService;
import com.moyin.tts.service.IMoyinTtsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Service
public class MoyinReportServiceImpl implements IMoyinReportService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private MoyinReportMapper reportMapper;

    @Resource
    private IMoyinTtsService ttsService;

    @Override
    public int getUserCount() {
        return reportMapper.getUserCount();
    }

    @Override
    public int getUserOnlineCount() {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        return keys.size();
    }

    @Override
    public int getSpeakerCount() {
        return reportMapper.getSpeakerCount();
    }

    @Override
    public int getEmotionCount() {
        return reportMapper.getEmotionCount();
    }

    @Override
    public int getTtsCount() {
        return reportMapper.getTtsCount();
    }

    @Override
    public int getTtsTextCount() {
        return reportMapper.getTtsTextCount();
    }

    @Override
    public List<UserTtsVo> getUserList() {
        return reportMapper.getUserList();
    }

    @Override
    public int getTodayTtsCountByUserId(String userName) {
        return reportMapper.getTodayTtsCountByUserId(userName);
    }

    @Override
    public int getTodayTtsRemainCountByUserId(Long userId) {
        return ttsService.getLimitCount() - getTodayTtsTextCountByUserId(userId);
    }

    @Override
    public int getTodayTtsTextCountByUserId(Long userId) {
        // 从缓存中获取
        Integer data = redisCache.getCacheObject("tts-limit:" + userId);
        if (data == null) {
            return 0;
        }
        return data;
    }

    @Override
    public int getMonthTtsCountByUserId(String userName) {
        return reportMapper.getMonthTtsCountByUserId(userName);
    }

    @Override
    public int getMonthTtsRemainCountByUserId(String userName) {
        return ttsService.getLimitCount() * getDaysInMonth() - reportMapper.getMonthTtsTextCountByUserId(userName);
    }

    private int getDaysInMonth() {
        // 创建一个Calendar实例
        Calendar calendar = Calendar.getInstance();
        // 获取当前月份的最大天数
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMonthTtsTextCountByUserId(String userName) {
        return reportMapper.getMonthTtsTextCountByUserId(userName);
    }
}
