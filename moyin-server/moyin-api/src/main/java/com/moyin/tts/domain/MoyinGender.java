package com.moyin.tts.domain;

import com.moyin.common.annotation.Excel;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 性别管理
 *
 * @author moyin
 * @date 2024-06-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinGender extends BaseEntity {

    /**
     * 性别ID
     */
    private Long id;

    /**
     * 性别名称
     */
    @Excel(name = "性别名称")
    private String name;

    /**
     * 性别状态
     */
    @Excel(name = "性别状态")
    private Long status;
}
