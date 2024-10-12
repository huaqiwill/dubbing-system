package com.moyin.tts.domain;

import com.moyin.common.core.domain.BaseEntity;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinAppKey {
    private Integer id;
    private String appKey;
    private String appSecret;
    private Integer status;
    private String notes;
}
