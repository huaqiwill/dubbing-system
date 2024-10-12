package com.moyin.tts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moyin.common.annotation.Excel;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinEmotion {

    @Excel(name = "情绪ID")
    private Long id;

    @Excel(name = "情绪名称")
    private String emotionName;

    private String emotionValue;

    @Excel(name = "情绪状态")
    private Integer emotionStatus;

    @Excel(name = "图片地址")
    private String imageUrl;

    @Excel(name = "显示排序")
    private Integer displayOrder;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
