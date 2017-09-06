package com.sfsctech.common.security.aes;

import com.sfsctech.common.util.HexUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * Class Aes
 *
 * @author 张麒 2016/5/27.
 * @version Description:
 */
public class Aes {

    // 盐值
    private static final String salt = "242DD6B633BB77C2A76FBDFE";

    public static String encrypt(String bef_aes) {
        return encrypt(bef_aes, salt);
    }

    public static String encrypt(String bef_aes, String password) {
        byte[] byteContent = null;
        try {
            byteContent = bef_aes.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt(byteContent, password);
    }

    public static String encrypt(byte[] content, String password) {
        try {
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String aft_aes) {
        return decrypt(aft_aes, salt);
    }

    public static String decrypt(String aft_aes, String password) {
        try {
            byte[] content = parseHexStr2Byte(aft_aes);
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int value = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
            result[i] = (byte) value;
        }
        return result;
    }

    public static SecretKey getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(128, secureRandom);
            return _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("初始化密钥出现异常");
        }
    }

    public static void main(String[] args) {
        String content = "tes发的时刻开房大厦fkldsfjslkdjfsd8538432-942jldskds fds jffld!@#$%^&*()_t";
        String password = "12345678";
        // 加密
        System.out.println("加密前：" + content);
        String s = encrypt(content, password);
        System.out.println("加密后：" + s);
        System.out.println("加密后：" + HexUtil.toHexString(s.getBytes()));
        // 解密

        String s1 = decrypt(s, password);
        System.out.println("解密后：" + s1);
    }

}
