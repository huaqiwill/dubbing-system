package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.domain.MoyinStyle;

import java.util.List;


public interface IMoyinStyleService {

    MoyinStyle selectById(Long id);

    List<MoyinStyle> selectList(MoyinStyle category);

    int insert(MoyinStyle category);

    int updateById(MoyinStyle category);

    int deleteByIds(Long[] ids);

    int deleteStyleById(Long id);
}
