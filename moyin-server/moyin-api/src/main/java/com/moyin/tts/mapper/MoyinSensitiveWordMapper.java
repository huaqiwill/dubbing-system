package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinSensitiveWord;

import java.util.List;


public interface MoyinSensitiveWordMapper {

    int updateSensitiveWordById(MoyinSensitiveWord lexicon);

    int deleteSensitiveWordById(Long id);

    MoyinSensitiveWord findSensitiveWordById(Long id);

    List<MoyinSensitiveWord> listSensitiveWord(MoyinSensitiveWord lexicon);

    List<MoyinSensitiveWord> selectAllSensitiveWord();

    int insertSensitiveWord(MoyinSensitiveWord lexicon);

    List<MoyinSensitiveWord> findSensitiveWordList(List<String> wordList);
}
