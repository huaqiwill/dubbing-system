package com.moyin.tts.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import java.util.Map;

/**
 * @author mobvoi
 * @date 2023/06/07
 **/
public class JsonUtil {

  private JsonUtil() {
  }

  public static JSONObject parseObject(String json) {
    return JSON.parseObject(json);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    return JSON.parseObject(json, clazz);
  }

  public static <T> T fromJson(String json, TypeReference<T> typeReference) {
    return JSON.parseObject(json, typeReference);
  }

  public static String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }

  public static Map<String, Object> toMap(Object obj) {
    return JSON.parseObject(JSON.toJSONString(obj));
  }

  public static Map<String, String> toMapStr(Object obj) {
    return JSON.parseObject(JSON.toJSONString(obj), new TypeReference<Map<String, String>>() {
    });
  }

}
