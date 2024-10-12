package com.moyin.tts.service;

import java.util.List;

import com.moyin.tts.model.vo.UserTtsVo;


public interface IMoyinReportService {
    int getUserCount();

    int getUserOnlineCount();

    int getSpeakerCount();

    int getEmotionCount();

    int getTtsCount();

    int getTtsTextCount();

    List<UserTtsVo> getUserList();

    int getTodayTtsCountByUserId(String userName);

    int getTodayTtsRemainCountByUserId(Long userId);

    int getTodayTtsTextCountByUserId(Long userId);

    int getMonthTtsCountByUserId(String userName);

    int getMonthTtsRemainCountByUserId(String userName);

    int getMonthTtsTextCountByUserId(String userName);
}
