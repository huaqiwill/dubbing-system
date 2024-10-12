package com.moyin.tts.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoyinTtsResultVo {
    private String audioText;
    private String audioUrl;
    private boolean success;
    private String errorMessage;
    private byte[] audioBytes;
    private byte[] srtBytes;
}
