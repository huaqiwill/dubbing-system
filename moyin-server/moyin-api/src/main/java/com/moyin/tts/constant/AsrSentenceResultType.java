package com.moyin.tts.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mobvoi
 * @date 2023/06/08
 **/
@Getter
@AllArgsConstructor
public enum AsrSentenceResultType {
    // 准备
    READY("server_ready"),
    // 错误
    ERROR("server_error"),
    //
    PARTIAL("partial_result"),
    //
    END("speech_end"),
    //
    SILENCE("silence");

    /**
     * 名字
     */
    private final String name;
}
