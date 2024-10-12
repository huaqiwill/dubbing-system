package com.moyin.tts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinTtsRecord {
    private String id;

    private String speaker;

    private String text;

    private Integer textLength;

    private String ssml;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String createBy;
}
