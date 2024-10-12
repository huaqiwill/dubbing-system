package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinLanguage;
import com.moyin.tts.mapper.MoyinLanguageMapper;
import com.moyin.tts.service.IMoyinLanguageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MoyinLanguageServiceImpl implements IMoyinLanguageService {

    @Resource
    private MoyinLanguageMapper languageMapper;

    @Override
    public MoyinLanguage selectById(Long id) {
        return languageMapper.selectById(id);
    }

    @Override
    public List<MoyinLanguage> selectList(MoyinLanguage language) {
        return languageMapper.selectList(language);
    }

    @Override
    public int insert(MoyinLanguage language) {
        language.setCreateTime(DateUtils.getNowDate());
        return languageMapper.insert(language);
    }

    @Override
    public int updateById(MoyinLanguage language) {
        language.setUpdateTime(DateUtils.getNowDate());
        return languageMapper.update(language);
    }


    @Override
    public int deleteByIds(Long[] ids) {
        return languageMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return languageMapper.deleteById(id);
    }
}
