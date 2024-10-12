package com.moyin.tts.model.dto;

import lombok.Data;

/**
 * @author 31734
 */
@Data
public class TtsRecordDTO {
    private Integer id;
    private String time;
    private String voice;
    private String content;
}
