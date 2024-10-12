package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinAge;
import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.mapper.MoyinAgeMapper;
import com.moyin.tts.service.IMoyinAgeService;
import com.moyin.tts.service.IMoyinCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MoyinAgeServiceImpl implements IMoyinAgeService {

    @Resource
    private MoyinAgeMapper ageMapper;


    @Override
    public MoyinAge selectById(Long id) {
        return ageMapper.selectAgeById(id);
    }

    @Override
    public List<MoyinAge> selectList(MoyinAge age) {
        return ageMapper.selectAgeList(age);
    }


    @Override
    public int insert(MoyinAge moyinCategory) {
        moyinCategory.setCreateTime(DateUtils.getNowDate());
        return ageMapper.insertAge(moyinCategory);
    }


    @Override
    public int updateById(MoyinAge moyinCategory) {
        moyinCategory.setUpdateTime(DateUtils.getNowDate());
        return ageMapper.updateAge(moyinCategory);
    }


    @Override
    public int deleteByIds(Long[] ids) {
        return ageMapper.deleteAgeByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return ageMapper.deleteAgeById(id);
    }
}
