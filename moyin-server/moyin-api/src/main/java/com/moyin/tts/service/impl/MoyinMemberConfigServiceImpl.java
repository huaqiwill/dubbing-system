package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinMemberConfigItem;
import com.moyin.tts.domain.MoyinMemberConfigPrice;
import com.moyin.tts.mapper.MoyinMemberConfigItemMapper;
import com.moyin.tts.mapper.MoyinMemberConfigPriceMapper;
import com.moyin.tts.service.IMoyinMemberConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 31734
 */
@Service
public class MoyinMemberConfigServiceImpl implements IMoyinMemberConfigService {

    @Resource
    private MoyinMemberConfigPriceMapper configPriceMapper;

    @Resource
    private MoyinMemberConfigItemMapper configItemMapper;

    @Override
    public int addPrice(MoyinMemberConfigPrice memberConfigPrice) {
        return configPriceMapper.addPrice(memberConfigPrice);
    }

    @Override
    public List<MoyinMemberConfigPrice> listPrice(MoyinMemberConfigPrice memberConfigPrice) {
        return configPriceMapper.listPrice(memberConfigPrice);
    }

    @Override
    public int editPrice(MoyinMemberConfigPrice memberConfigPrice) {
        return configPriceMapper.editPrice(memberConfigPrice);
    }

    @Override
    public int deletePrice(Long id) {
        return configPriceMapper.deletePrice(id);
    }

    @Override
    public int addItem(MoyinMemberConfigItem memberConfigItem) {
        return configItemMapper.addItem(memberConfigItem);
    }

    @Override
    public int deleteItem(Long id) {
        return configItemMapper.deleteItem(id);
    }

    @Override
    public List<MoyinMemberConfigItem> listItem(MoyinMemberConfigItem memberConfigItem) {
        return configItemMapper.listItem(memberConfigItem);
    }

    @Override
    public int editItem(MoyinMemberConfigItem memberConfigItem) {
        return configItemMapper.editItem(memberConfigItem);
    }
}
