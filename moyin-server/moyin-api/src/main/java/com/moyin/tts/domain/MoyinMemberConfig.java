package com.moyin.tts.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class MoyinMemberConfig {
    private String name;
    private String describe;
    private List<MoyinMemberConfigPrice> memberConfigPrices;
}
