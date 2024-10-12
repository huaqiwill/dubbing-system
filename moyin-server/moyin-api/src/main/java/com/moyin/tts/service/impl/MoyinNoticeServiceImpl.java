package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinNotice;
import com.moyin.tts.mapper.MoyinNoticeMapper;
import com.moyin.tts.service.IMoyinMemberService;
import com.moyin.tts.service.IMoyinNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 31734
 */
@Service
public class MoyinNoticeServiceImpl implements IMoyinNoticeService {

    @Resource
    private MoyinNoticeMapper moyinNoticeMapper;

    @Override
    public int insert(MoyinNotice moyinCategory) {
        return moyinNoticeMapper.insert(moyinCategory);
    }

    @Override
    public int updateById(MoyinNotice moyinCategory) {
        return moyinNoticeMapper.updateById(moyinCategory);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return moyinNoticeMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return moyinNoticeMapper.deleteById(id);
    }

    @Override
    public List<MoyinNotice> selectList(MoyinNotice moyinCategory) {
        return moyinNoticeMapper.selectList(moyinCategory);
    }

    @Override
    public MoyinNotice selectById(Long id) {
        return moyinNoticeMapper.selectById(id);
    }
}
