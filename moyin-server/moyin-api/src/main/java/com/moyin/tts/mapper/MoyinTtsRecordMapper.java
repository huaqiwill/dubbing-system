package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinTtsRecord;

import java.util.List;

public interface MoyinTtsRecordMapper {

    int insert(MoyinTtsRecord record);

    List<MoyinTtsRecord> listRecord(MoyinTtsRecord record);

    int deleteById(Long id);

    MoyinTtsRecord selectById(Long id);

    List<MoyinTtsRecord> ttsRecordListAll(MoyinTtsRecord moyinTtsRecord);
}
