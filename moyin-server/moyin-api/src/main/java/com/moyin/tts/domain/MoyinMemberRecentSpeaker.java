package com.moyin.tts.domain;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinMemberRecentSpeaker {
    private Long userId;
    private Long speakerId;
    private Date createTime;
}
