package com.moyin.tts.domain;

import com.moyin.common.annotation.Excel;
import com.moyin.common.core.domain.BaseEntity;
import com.moyin.tts.model.vo.MoyinSpeakerEmotionVo;
import lombok.*;

import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinSpeaker {

    @Excel(name = "编号")
    private Long id;

    @Excel(name = "发音人名称")
    private String name;

    @Excel(name = "发音人头像")
    private String headerImage;

    @Excel(name = "发音人行为")
    private String behavior;

    @Excel(name = "发音人性别")
    private String gender;

    @Excel(name = "发音人年龄")
    private Integer age;

    @Excel(name = "风格调用名")
    private String styleCallName;

    @Excel(name = "标签")
    private String tags;

    private String alias;

    private Set<Long> domainIdSet;

    private String domain;

    private Set<Long> emotionIdSet;

    private String emotion;

    private Set<Long> languageIdSet;

    private List<MoyinSpeakerEmotionVo> emotionDataList;

    private String language;

    private Integer status;

    private Boolean isStar;
}
