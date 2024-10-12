package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinLexicon;

import java.util.List;

public interface IMoyinLexiconService {
    int updateLexiconById(MoyinLexicon lexicon);

    int deleteLexiconById(Long id);

    MoyinLexicon findLexiconById(Long id);

    List<MoyinLexicon> listLexicon(MoyinLexicon lexicon);

    int insertLexicon(MoyinLexicon lexicon);
}
