package com.moyin.tts.domain;

import com.moyin.common.annotation.Excel;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinCategory extends BaseEntity {

    private Long id;

    @Excel(name = "分类名称")
    private String name;

    @Excel(name = "分类状态")
    private Long status;
}
