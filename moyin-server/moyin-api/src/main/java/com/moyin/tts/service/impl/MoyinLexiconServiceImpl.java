package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinLexicon;
import com.moyin.tts.mapper.MoyinLexiconMapper;
import com.moyin.tts.service.IMoyinLexiconService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MoyinLexiconServiceImpl implements IMoyinLexiconService {

    @Resource
    private MoyinLexiconMapper lexiconMapper;

    @Override
    public int updateLexiconById(MoyinLexicon lexicon) {
        return lexiconMapper.updateById(lexicon);
    }

    @Override
    public int deleteLexiconById(Long id) {
        return lexiconMapper.deleteById(id);
    }

    @Override
    public MoyinLexicon findLexiconById(Long id) {
        return lexiconMapper.findById(id);
    }

    @Override
    public List<MoyinLexicon> listLexicon(MoyinLexicon lexicon) {
        return lexiconMapper.list(lexicon);
    }

    @Override
    public int insertLexicon(MoyinLexicon lexicon) {
        return lexiconMapper.insert(lexicon);
    }
}
