package com.moyin.tts.domain;

import lombok.*;
import org.apache.poi.hpsf.Decimal;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinMemberConfigPrice {
    private Long id;
    private String name;
    private Long price;
    private List<MoyinMemberConfigItem> memberConfigItems;
}
