package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinMember;
import com.moyin.tts.mapper.MoyinMemberMapper;
import com.moyin.tts.service.IMoyinMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MoyinMemberServiceImpl implements IMoyinMemberService {

    @Resource
    private MoyinMemberMapper moyinMemberMapper;

    @Override
    public int insert(MoyinMember moyinMember) {
        return moyinMemberMapper.insert(moyinMember);
    }

    @Override
    public int updateById(MoyinMember moyinMember) {
        return moyinMemberMapper.updateById(moyinMember);
    }

    @Override
    public int deleteById(Long id) {
        return moyinMemberMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return moyinMemberMapper.deleteByIds(ids);
    }

    @Override
    public List<MoyinMember> selectList(MoyinMember moyinCategory) {
        return moyinMemberMapper.selectList(moyinCategory);
    }

    @Override
    public String selectById(Long id) {
        return moyinMemberMapper.selectById(id);
    }
}
