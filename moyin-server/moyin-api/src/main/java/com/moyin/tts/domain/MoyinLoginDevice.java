package com.moyin.tts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MoyinLoginDevice {

    private Long id;

    private Long userId;

    private String userName;

    private String ip;

    private String userAgent;

    private String browser;

    private String os;

    private String device;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    private boolean online;
}
