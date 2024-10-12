package com.moyin.tts.model.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MoyinEmotionVo {
    private Long id;
    private String name;
    private String imageUrl;
    private String value;
}
