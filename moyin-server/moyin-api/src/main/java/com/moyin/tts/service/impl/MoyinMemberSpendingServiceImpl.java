package com.moyin.tts.service.impl;

import com.moyin.tts.mapper.MoyinMemberSpendingMapper;
import com.moyin.tts.service.IMoyinMemberService;
import com.moyin.tts.service.IMoyinMemberSpendingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 魔音会员消费记录
 *
 * @author 31734
 */
@Service
public class MoyinMemberSpendingServiceImpl implements IMoyinMemberSpendingService {
    @Resource
    private MoyinMemberSpendingMapper spendingMapper;


}
