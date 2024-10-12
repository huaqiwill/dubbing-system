package com.moyin.tts.service.impl;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.moyin.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import com.moyin.tts.mapper.MoyinEmotionMapper;
import com.moyin.tts.domain.MoyinEmotion;
import com.moyin.tts.service.IMoyinEmotionService;

import javax.annotation.Resource;

@Slf4j
@Service
public class MoyinEmotionServiceImpl implements IMoyinEmotionService {

    @Resource
    private MoyinEmotionMapper emotionMapper;

    @Override
    public MoyinEmotion selectById(Long id) {
        return emotionMapper.selectById(id);
    }

    @Override
    public List<MoyinEmotion> selectList(MoyinEmotion moyinEmotion) {
        return emotionMapper.selectList(moyinEmotion);
    }

    @Override
    public int insert(MoyinEmotion moyinEmotion) {
        moyinEmotion.setCreateTime(DateUtils.getNowDate());
        return emotionMapper.insert(moyinEmotion);
    }

    @Override
    public int update(MoyinEmotion moyinEmotion) {
        moyinEmotion.setUpdateTime(DateUtils.getNowDate());
        return emotionMapper.update(moyinEmotion);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return emotionMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return emotionMapper.deleteById(id);
    }

}
