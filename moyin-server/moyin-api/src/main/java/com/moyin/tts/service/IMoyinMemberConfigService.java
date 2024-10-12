package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinMemberConfigItem;
import com.moyin.tts.domain.MoyinMemberConfigPrice;

import java.util.List;

/**
 * @author 31734
 */
public interface IMoyinMemberConfigService {

    /**
     * @param memberConfigPrice
     * @return
     */
    int addPrice(MoyinMemberConfigPrice memberConfigPrice);

    /**
     * @param memberConfigPrice
     * @return
     */
    List<MoyinMemberConfigPrice> listPrice(MoyinMemberConfigPrice memberConfigPrice);

    /**
     * @param memberConfigPrice
     * @return
     */
    int editPrice(MoyinMemberConfigPrice memberConfigPrice);

    /**
     * @param id
     * @return
     */
    int deletePrice(Long id);

    /**
     * @param memberConfigItem
     * @return
     */
    int addItem(MoyinMemberConfigItem memberConfigItem);

    /**
     * @param id
     * @return
     */
    int deleteItem(Long id);

    /**
     * @param memberConfigItem
     * @return
     */
    List<MoyinMemberConfigItem> listItem(MoyinMemberConfigItem memberConfigItem);

    /**
     * @param memberConfigItem
     * @return
     */
    int editItem(MoyinMemberConfigItem memberConfigItem);
}
