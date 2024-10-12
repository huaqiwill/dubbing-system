package com.moyin.tts.model.vo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinSpeakerVo {
    private Integer id;
    private String name;
    private String headerImage;
}
