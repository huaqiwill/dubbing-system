package com.moyin.tts.model.dto;

import lombok.Data;

@Data
public class VoiceDTO {
    private Integer id;
    private String description;
    private String gender;
    private String label;
    private String labelLevel;
    private String language;
    private String voiceAudioPath;
    private String voiceName;
}
