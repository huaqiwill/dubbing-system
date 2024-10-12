package com.moyin.tts.exception;

/**
 * @author mobvoi
 * @date 2023/06/07
 **/
public class OpenapiException extends RuntimeException {

  public OpenapiException(String message) {
    super(message);
  }
}
