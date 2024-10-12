package com.moyin.tts.mapper;

import java.util.List;

import com.moyin.tts.domain.MoyinEmotion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MoyinEmotionMapper {

    MoyinEmotion selectById(Long id);

    List<MoyinEmotion> selectList(MoyinEmotion moyinEmotion);

    int insert(MoyinEmotion moyinEmotion);

    int update(MoyinEmotion moyinEmotion);

    int deleteById(Long id);

    int deleteByIds(Long[] ids);
}
