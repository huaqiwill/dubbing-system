package com.moyin.tts.service;

import java.util.List;

import com.moyin.tts.domain.MoyinDict;
import com.moyin.tts.domain.MoyinDomain;
import com.moyin.tts.domain.MoyinEmotion;

public interface IMoyinEmotionService {

    MoyinEmotion selectById(Long id);

    List<MoyinEmotion> selectList(MoyinEmotion domain);

    int insert(MoyinEmotion domain);

    int update(MoyinEmotion domain);

    int deleteByIds(Long[] ids);

    int deleteById(Long id);

}
