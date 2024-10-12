package com.moyin.tts.model.vo;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserTtsVo {
    private String userName;
    private String nickName;
    private Long userId;
    private Integer ttsCount;
    private Integer ttsTextCount;
    private Integer ttsRemainCount;
}
