package com.moyin.tts.domain;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinMemberConfigItem {
    private Long id;
    private String name;
    private String describe;
    private String headerImage;
}
