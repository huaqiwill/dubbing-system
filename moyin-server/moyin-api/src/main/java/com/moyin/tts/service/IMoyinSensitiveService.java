package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinSensitiveWord;

import java.util.List;

/**
 * 敏感词服务
 *
 * @author 31734
 */
public interface IMoyinSensitiveService {

    int updateSensitiveById(MoyinSensitiveWord lexicon);

    int deleteSensitiveById(Long id);

    MoyinSensitiveWord findSensitiveWordById(Long id);

    List<MoyinSensitiveWord> listSensitiveWord(MoyinSensitiveWord lexicon);

    int insertSensitive(MoyinSensitiveWord lexicon);

    List<MoyinSensitiveWord> identify(String text);
}
