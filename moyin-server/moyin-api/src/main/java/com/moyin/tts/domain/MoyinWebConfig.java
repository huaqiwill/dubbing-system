package com.moyin.tts.domain;

import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinWebConfig {

    private String globalAppKey;

    private String globalAppSecret;

    private String title;

    private Integer enableMoyinApi;
}
