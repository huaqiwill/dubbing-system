package com.moyin.tts.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 31734
 */
public class Md5Util {
    public static void main(String[] args) {
        String input = "Hello, World!";
        String md5Hash = getMD5Hash(input);
        System.out.println("MD5 Hash: " + md5Hash);
    }

    public static String getMD5Hash(String input) {
        try {
            // 创建MD5摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算输入字符串的MD5哈希值
            byte[] messageDigest = md.digest(input.getBytes());
            // 转换为16进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }
}
