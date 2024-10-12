package com.moyin.tts.model.vo;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinTtsRequestVo {

    private String text;

    private String rawText;

    private String speaker;

    private String audioType;

    private Float speed;

    private String convert;

    private Long rate;

    private Float volume;

    private Float pitch;

    private String symbolSil;

    private boolean ignoreLimit;

    private boolean genSrt;

    private boolean mergeSymbol;
}
