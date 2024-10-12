package com.moyin.tts.domain;

import com.moyin.common.annotation.Excel;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 语言管理
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
public class MoyinLanguage extends BaseEntity {


    private Long id;

    @Excel(name = "分类名称")
    private String name;

    @Excel(name = "分类状态")
    private Long status;

    private String value;
}
