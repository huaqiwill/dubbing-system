package com.moyin.tts.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mobvoi
 * @date 2023/06/08
 **/
@Getter
@AllArgsConstructor
public enum VideoComposeStatus {
    //
    ING("ing", "处理中"),
    //
    SUCCESS("suc", "成功"),
    //
    FAIL("fail", "失败");

    /**
     *
     */
    private final String name;
    /**
     *
     */
    private final String desc;
}
