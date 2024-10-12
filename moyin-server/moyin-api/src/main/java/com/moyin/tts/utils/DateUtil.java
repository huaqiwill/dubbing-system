package com.moyin.tts.utils;

/**
 * @author mobvoi
 * @date 2023/06/07
 **/
public class DateUtil {

    private DateUtil() {
    }

    public static long currentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public static String timestamp() {
        return String.valueOf(currentTimeSeconds());
    }

}
