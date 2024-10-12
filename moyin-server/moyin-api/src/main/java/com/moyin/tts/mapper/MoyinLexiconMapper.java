package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinLexicon;

import java.util.List;

/**
 * 词库 Mapper
 *
 * @author 31734
 */
public interface MoyinLexiconMapper {

    int updateById(MoyinLexicon lexicon);

    int deleteById(Long id);

    MoyinLexicon findById(Long id);

    List<MoyinLexicon> list(MoyinLexicon lexicon);

    int insert(MoyinLexicon lexicon);
}
