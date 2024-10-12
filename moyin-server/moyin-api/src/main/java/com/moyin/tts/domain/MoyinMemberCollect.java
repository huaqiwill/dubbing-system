package com.moyin.tts.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinMemberCollect {
    private Long id;
    private Long userId;
    private Long speakerId;
    private String speakerNotes;

    private String userName;
    private String speakerName;
}
