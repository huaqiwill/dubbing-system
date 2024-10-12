package com.moyin.tts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinNotice extends BaseEntity {

    private Integer id;

    private String title;

    private String content;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
