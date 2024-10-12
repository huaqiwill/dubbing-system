package com.moyin.tts.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mobvoi
 * @date 2023/06/08
 **/
@Getter
@AllArgsConstructor
public enum AsrFileTaskStatus {
    // 提交
    SUBMIT(1, "提交"),
    // 成功
    SUCCESS(2, "成功"),
    // 失败
    FAIL(3, "失败");

    /**
     * 代码
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String desc;
}
