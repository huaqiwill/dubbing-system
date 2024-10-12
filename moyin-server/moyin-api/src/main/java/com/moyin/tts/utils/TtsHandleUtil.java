package com.moyin.tts.utils;

/**
 * @author 31734
 */
public class TtsHandleUtil {

    private final static Character SEMI = '"';

    /**
     * 处理ssml表达式
     *
     * @param ssml ssml表达式
     * @return 处理过后的ssml表达式
     */
    public static String handleSsml(String ssml) {
        if (ssml.charAt(0) == SEMI && ssml.charAt(ssml.length() - 1) == SEMI) {
            return ssml.substring(1, ssml.length() - 1).replace("\\\"", "\"");
        } else {
            return ssml;
        }
    }
}
