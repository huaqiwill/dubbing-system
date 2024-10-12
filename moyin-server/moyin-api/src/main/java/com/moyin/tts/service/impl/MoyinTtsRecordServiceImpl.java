package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinTtsRecord;
import com.moyin.tts.mapper.MoyinTtsRecordMapper;
import com.moyin.tts.service.IMoyinTtsRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 31734
 */
@Service
public class MoyinTtsRecordServiceImpl implements IMoyinTtsRecordService {

    @Resource
    private MoyinTtsRecordMapper recordMapper;

    private boolean isAnsi(char c) {
        // ASCII 字符范围为 0 到 127
        return c <= 127;
    }

    // 计算字符长度的方法
    public int calculateDubbingLength(String text) {
        int dubbingLength = 0;
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if (isAnsi(c)) {
                dubbingLength += 1;
            } else {
                dubbingLength += 2;
            }
        }
        return dubbingLength;
    }

    @Override
    public int insert(MoyinTtsRecord ttsRecord) {
        ttsRecord.setTextLength(calculateDubbingLength(ttsRecord.getText()));
        return recordMapper.insert(ttsRecord);
    }

    @Override
    public List<MoyinTtsRecord> ttsRecordList(MoyinTtsRecord ttsRecord) {
        return recordMapper.listRecord(ttsRecord);
    }

    @Override
    public int deleteById(Long id) {
        return recordMapper.deleteById(id);
    }

    @Override
    public MoyinTtsRecord selectById(Long id) {
        return recordMapper.selectById(id);
    }

    @Override
    public List<MoyinTtsRecord> ttsRecordListAll(MoyinTtsRecord ttsRecord) {
        return recordMapper.ttsRecordListAll(ttsRecord);
    }
}
