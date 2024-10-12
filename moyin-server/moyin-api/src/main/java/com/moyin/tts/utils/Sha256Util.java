package com.moyin.tts.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @author 31734
 */
public class Sha256Util {
    public static void main(String[] args) {
        System.out.println(Sha256Util.getSha256Hash("你好".getBytes()));
    }

    /**
     * 生成给定字节数组的SHA-256哈希值的十六进制字符串表示。
     *
     * @param originalBytes 待哈希的字节数组
     * @return 返回SHA-256哈希值的十六进制字符串
     */
    public static String getSha256Hash(byte[] originalBytes) {
        // 使用try-with-resources语句自动关闭MessageDigest实例
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 对原始字节数组进行哈希计算
            byte[] encodedHash = digest.digest(originalBytes);

            // 将哈希值转换为十六进制字符串表示
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // 返回哈希值的十六进制字符串
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            // 在这里处理异常，比如抛出运行时异常
            throw new RuntimeException("SHA-256 MessageDigest not available", e);
        }
    }
}
