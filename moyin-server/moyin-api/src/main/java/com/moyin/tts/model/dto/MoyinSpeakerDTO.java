package com.moyin.tts.model.dto;

import com.moyin.common.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class MoyinSpeakerDTO {

    @NotNull
    private Long id;

    private String name;

    private String headerImage;

    private String behavior;

    private String gender;

    private Integer age;

    private String styleCallName;

    private String tags;

    private String alias;

    private Set<Long> domainIdSet;

    private String domain;

    private Set<Long> emotionIdSet;

    private String emotion;

    private Set<Long> languageIdSet;

    private String language;
}
