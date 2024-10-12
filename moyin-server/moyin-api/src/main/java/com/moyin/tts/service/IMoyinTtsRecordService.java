package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinTtsRecord;

import java.util.List;

/**
 * @author 31734
 */
public interface IMoyinTtsRecordService {

    int calculateDubbingLength(String text);

    int insert(MoyinTtsRecord ttsRecord);

    List<MoyinTtsRecord> ttsRecordList(MoyinTtsRecord ttsRecord);

    int deleteById(Long id);

    MoyinTtsRecord selectById(Long id);

    List<MoyinTtsRecord> ttsRecordListAll(MoyinTtsRecord ttsRecord);
}
