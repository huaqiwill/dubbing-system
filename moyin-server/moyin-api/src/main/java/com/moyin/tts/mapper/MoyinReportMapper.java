package com.moyin.tts.mapper;

import com.moyin.tts.model.vo.UserTtsVo;

import java.util.List;

public interface MoyinReportMapper {
    int getUserCount();

    int getUserOnlineCount();

    int getSpeakerCount();

    int getEmotionCount();

    int getTtsCount();

    int getTtsTextCount();

    List<UserTtsVo> getUserList();

    int getTodayTtsCountByUserId(String userName);

    int getMonthTtsCountByUserId(String userName);

    int getMonthTtsTextCountByUserId(String userName);
}
