package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinMemberConfigItem;
import com.moyin.tts.domain.MoyinMemberConfigPrice;

import java.util.List;

/**
 * @author 31734
 */
public interface MoyinMemberConfigItemMapper {

    /**
     * @param memberConfigItem
     * @return
     */
    int editItem(MoyinMemberConfigItem memberConfigItem);

    /**
     * @param memberConfigItem
     * @return
     */
    List<MoyinMemberConfigItem> listItem(MoyinMemberConfigItem memberConfigItem);

    /**
     * @param id
     * @return
     */
    int deleteItem(Long id);

    /**
     * @param memberConfigItem
     * @return
     */
    int addItem(MoyinMemberConfigItem memberConfigItem);
}
