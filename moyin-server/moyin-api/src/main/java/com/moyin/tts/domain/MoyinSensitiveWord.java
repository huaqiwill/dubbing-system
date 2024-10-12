package com.moyin.tts.domain;

import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinSensitiveWord {

    private Long id;

    private String word;

    private Long lexiconId;

    private String lexiconName;
}
