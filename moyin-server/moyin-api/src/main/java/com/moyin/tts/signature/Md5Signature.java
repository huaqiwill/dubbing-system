package com.moyin.tts.signature;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author mobvoi
 * @date 2023/06/07
 **/
public class Md5Signature implements Signature {

    private static Md5Signature instance = new Md5Signature();

    public static Md5Signature getInstance() {
        return instance;
    }

    @Override
    public String digest(String appKey, String appSecret, String timestamp) {
        return DigestUtils.md5Hex(String.join("+", appKey, appSecret, timestamp));
    }

}
