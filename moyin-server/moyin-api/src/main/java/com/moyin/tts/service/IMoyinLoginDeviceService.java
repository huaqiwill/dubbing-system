package com.moyin.tts.service;

import com.moyin.common.annotation.Log;
import com.moyin.tts.domain.MoyinLoginDevice;

import java.util.List;

public interface IMoyinLoginDeviceService {

    List<MoyinLoginDevice> list(MoyinLoginDevice loginDevice);

    int remove(Long id);

    int add(Long userId, String userAgent, String ip);

    MoyinLoginDevice info(Long id);

}
