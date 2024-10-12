package com.moyin.tts.model.vo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinSpeakerEmotionVo {
    private String emotionId;
    private String speakerId;
    private String demo;
    private String demoUrl;
    private String styleCallName;
    private String name;
}
