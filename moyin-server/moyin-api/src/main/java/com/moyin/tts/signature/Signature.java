package com.moyin.tts.signature;

/**
 * @author mobvoi
 * @date 2023/06/07
 **/
public interface Signature {

  String digest(String appKey, String appSecret, String timestamp);

}
