package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinAge;
import com.moyin.tts.mapper.MoyinGenderMapper;
import com.moyin.tts.service.IMoyinAgeService;
import com.moyin.tts.service.IMoyinGenderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MoyinGenderServiceImpl implements IMoyinGenderService {

    @Resource
    private MoyinGenderMapper genderMapper;


    @Override
    public MoyinAge selectById(Long id) {
        return genderMapper.selectAgeById(id);
    }


    @Override
    public List<MoyinAge> selectList(MoyinAge age) {
        return genderMapper.selectAgeList(age);
    }


    @Override
    public int insert(MoyinAge moyinCategory) {
        moyinCategory.setCreateTime(DateUtils.getNowDate());
        return genderMapper.insertAge(moyinCategory);
    }


    @Override
    public int updateById(MoyinAge moyinCategory) {
        moyinCategory.setUpdateTime(DateUtils.getNowDate());
        return genderMapper.updateAge(moyinCategory);
    }


    @Override
    public int deleteByIds(Long[] ids) {
        return genderMapper.deleteAgeByIds(ids);
    }

    @Override
    public int deleteAgeById(Long id) {
        return genderMapper.deleteAgeById(id);
    }
}
