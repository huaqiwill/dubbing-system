package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinMember;

import java.util.List;

public interface IMoyinMemberService {

    int insert(MoyinMember moyinMember);

    int updateById(MoyinMember moyinMember);

    int deleteByIds(Long[] ids);

    int deleteById(Long id);

    List<MoyinMember> selectList(MoyinMember moyinMember);

    String selectById(Long id);
}
