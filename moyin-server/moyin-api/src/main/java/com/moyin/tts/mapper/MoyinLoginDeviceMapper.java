package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinLoginDevice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoyinLoginDeviceMapper {

    int delete(Long id);

    MoyinLoginDevice selectById(Long id);

    List<MoyinLoginDevice> selectList(MoyinLoginDevice loginDevice);

    void insert(MoyinLoginDevice build);

    MoyinLoginDevice selectByUserId(Long userId);
}
