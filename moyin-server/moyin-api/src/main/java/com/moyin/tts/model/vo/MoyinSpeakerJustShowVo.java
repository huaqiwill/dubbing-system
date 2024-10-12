package com.moyin.tts.model.vo;

import lombok.*;

import javax.validation.constraints.AssertTrue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MoyinSpeakerJustShowVo {
    private Long id;
    private String name;
    private String alias;
    private String headerImage;
}
