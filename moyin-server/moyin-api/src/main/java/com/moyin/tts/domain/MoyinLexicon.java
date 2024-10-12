package com.moyin.tts.domain;

import com.moyin.common.core.controller.BaseController;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinLexicon {

    private Long id;

    private String name;

    private Integer status;

    private String describe;

    private String headerImage;
}
