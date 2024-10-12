package com.moyin.tts.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SpeakerDataDTO {
    private Long emotionId;
    private String name;
    private Long domainId;
    private Long languageId;
    private Long userId;
}
