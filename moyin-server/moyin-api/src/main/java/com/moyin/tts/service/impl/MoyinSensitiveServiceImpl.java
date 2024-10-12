package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinSensitiveWord;
import com.moyin.tts.mapper.MoyinSensitiveWordMapper;
import com.moyin.tts.service.IMoyinSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 31734
 */
@Service
@Slf4j
public class MoyinSensitiveServiceImpl implements IMoyinSensitiveService {

    @Resource
    private MoyinSensitiveWordMapper sensitiveWordMapper;

    @Override
    public int updateSensitiveById(MoyinSensitiveWord lexicon) {
        return sensitiveWordMapper.updateSensitiveWordById(lexicon);
    }

    @Override
    public int deleteSensitiveById(Long id) {
        return sensitiveWordMapper.deleteSensitiveWordById(id);
    }

    @Override
    public MoyinSensitiveWord findSensitiveWordById(Long id) {
        return sensitiveWordMapper.findSensitiveWordById(id);
    }

    @Override
    public List<MoyinSensitiveWord> listSensitiveWord(MoyinSensitiveWord lexicon) {
        return sensitiveWordMapper.listSensitiveWord(lexicon);
    }

    @Override
    public int insertSensitive(MoyinSensitiveWord lexicon) {
        return sensitiveWordMapper.insertSensitiveWord(lexicon);
    }

    @Override
    public List<MoyinSensitiveWord> identify(String text) {

        List<MoyinSensitiveWord> words = sensitiveWordMapper.selectAllSensitiveWord();

        return words.stream()
                .filter(sensitiveWord -> text.contains(sensitiveWord.getWord()))
                .collect(Collectors.toList());
    }
}
