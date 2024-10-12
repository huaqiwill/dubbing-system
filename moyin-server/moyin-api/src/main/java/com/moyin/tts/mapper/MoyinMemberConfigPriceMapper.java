package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinMemberConfigItem;
import com.moyin.tts.domain.MoyinMemberConfigPrice;

import java.util.List;

public interface MoyinMemberConfigPriceMapper {

    int deletePrice(Long id);

    int editPrice(MoyinMemberConfigPrice memberConfigPrice);

    List<MoyinMemberConfigPrice> listPrice(MoyinMemberConfigPrice memberConfigPrice);

    int addPrice(MoyinMemberConfigPrice memberConfigPrice);

}
