package com.sfsctech.common.support.security.aes;

import com.sfsctech.common.core.base.constants.LabelConstants;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
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
    private static final String SALT = "242DD6B633BB77C2A76FBDFE";
    // 16位盐值
    private static final String CBC_SALT = "242D#6B6%3B&7@C!";
    // 向量
    private static final String CBC_IV = "K(R9304J@$O8K*17";

    /**
     * 默认盐值加密
     *
     * @param bef_aes 需要加密的数据
     * @return aes加密数据
     */
    public static String encrypt(String bef_aes) {
        return encrypt(bef_aes, SALT);
    }

    /**
     * 自定义盐值加密
     *
     * @param bef_aes 需要加密的数据
     * @param salt    自定义盐值
     * @return aes加密数据
     */
    public static String encrypt(String bef_aes, String salt) {
        byte[] byteContent = null;
        try {
            byteContent = bef_aes.getBytes(LabelConstants.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt(byteContent, salt);
    }

    /**
     * 自定义盐值加密
     *
     * @param content 需要加密的数据
     * @param salt    自定义盐值
     * @return aes加密数据
     */
    public static String encrypt(byte[] content, String salt) {
        try {
            SecretKey secretKey = getKey(salt);
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

    /**
     * 默认盐值/向量加密
     *
     * @param bef_aes 需要加密的数据
     * @return aes加密数据
     */
    public static String encryptCBC(String bef_aes) {
        return encryptCBC(bef_aes, CBC_SALT, CBC_IV.getBytes());
    }

    /**
     * 自定义盐值/向量加密
     *
     * @param bef_aes 需要加密的数据
     * @param salt    自定义盐值
     * @param iv      自定义向量
     * @return aes加密数据
     */
    public static String encryptCBC(String bef_aes, String salt, byte[] iv) {
        try {
            SecretKey secretKey = getKey(salt);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
            byte[] result = cipher.doFinal(bef_aes.getBytes(LabelConstants.UTF8));
            return new BASE64Encoder().encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 默认盐值解密
     *
     * @param aft_aes 需要解密的数据
     * @return aes解密数据
     */
    public static String decrypt(String aft_aes) {
        return decrypt(aft_aes, SALT);
    }

    /**
     * 自定义盐值解密
     *
     * @param aft_aes 需要解密的数据
     * @param salt    盐值
     * @return aes解密数据
     */
    public static String decrypt(String aft_aes, String salt) {
        try {
            byte[] content = parseHexStr2Byte(aft_aes);
            SecretKey secretKey = getKey(salt);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            assert content != null;
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 默认盐值解密
     *
     * @param aft_aes 需要解密的数据
     * @return aes解密数据
     */
    public static String decryptCBC(String aft_aes) {
        return decryptCBC(aft_aes, CBC_SALT, CBC_IV.getBytes());
    }

    /**
     * 自定义盐值解密
     *
     * @param aft_aes 需要解密的数据
     * @param salt    盐值
     * @return aes解密数据
     */
    public static String decryptCBC(String aft_aes, String salt, byte[] iv) {
        try {
            byte[] content = new BASE64Decoder().decodeBuffer(aft_aes);
            SecretKey secretKey = getKey(salt);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
            assert content != null;
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseByte2HexStr(byte buf[]) {
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

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int value = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
            result[i] = (byte) value;
        }
        return result;
    }

    private static SecretKey getKey(String strKey) {
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
//        String content = "tes发的时刻开房大厦fkldsfjslkdjfsd8538432-942jldskds fds jffld!@#$%^&*()_t";
//        String password = "12345678";
//        // 加密
//        System.out.println("加密前：" + content);
//        String s = encrypt(content, password);
//        System.out.println("加密后：" + s);
//        System.out.println("加密后：" + HexUtil.toHexString(s.getBytes()));
//        // 解密
//
//        String s1 = decrypt(s, password);
//        System.out.println("解密后：" + s1);
//        String content = "PartnerCode=AonHewitt&OrganizationCode=efesco&UserId=001&UserName=FordTestUser&Service=AonSSo&Timestamp=" + System.currentTimeMillis();
//        System.out.println("明文：" + content);
//        System.out.println("密文：" + encryptCBC(content));
//        System.out.println("密钥：" + CBC_SALT);
//        System.out.println("向量：{0x4b, 0x28, 0x52, 0x39, 0x33, 0x30, 0x34, 0x4a, 0x40, 0x24, 0x4f, 0x38, 0x4b, 0x2a, 0x31, 0x37}");
//
//        System.out.println(new String(new byte[]{0x4b, 0x28, 0x52, 0x39, 0x33, 0x30, 0x34, 0x4a, 0x40, 0x24, 0x4f, 0x38, 0x4b, 0x2a, 0x31, 0x37}));
//        System.out.println(decryptCBC(encryptCBC(content)));
//        String t = encryptCBC(content, "D%H@6przcRAs7@#3", new byte[]{0x48, 0x45, 0x4c, 0x4c, 0x4f, 0x57, 0x4f, 0x52, 0x4c, 0x44, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36});
//        System.out.println(t);
//        System.out.println(Aes.decryptCBC(t, "D%H@6przcRAs7@#3", new byte[]{0x48, 0x45, 0x4c, 0x4c, 0x4f, 0x57, 0x4f, 0x52, 0x4c, 0x44, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36}));

//        System.out.println("121");
//        System.out.println(Aes.decryptCBC("/c5dIBxRFq/HNlyRicHnwbt+l915myvBLqpLN01i7uP9vXsg8O275j8jDBCVZrpWUMYo8KnLbLvRiGCc4o5MB7K1DJ5DaKdojWItf57/lse+D81RKU2vRkfkPVq4WCyb+e9eAKEHbx4dZL5EYrNPw2w+pjCZwpnVYodgTxwcHJcAcYDQqN0h8jOyNeYRPmLT", "D%H@6przcRAs7@#3", new byte[]{0x48, 0x45, 0x4c, 0x4c, 0x4f, 0x57, 0x4f, 0x52, 0x4c, 0x44, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36}));
//        System.out.println(Aes.decryptCBC("/c5dIBxRFq/HNlyRicHnwbt+l915myvBLqpLN01i7uP9vXsg8O275j8jDBCVZrpWUMYo8KnLbLvRiGCc4o5MB7K1DJ5DaKdojWItf57/lse+D81RKU2vRkfkPVq4WCyb+e9eAKEHbx4dZL5EYrNPw2w+pjCZwpnVYodgTxwcHJcAcYDQqN0h8jOyNeYRPmLT", "D%H@6przcRAs7@#3", new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF}));

//        System.out.println(Aes.decrypt("AES", "D%H@6przcRAs7@#3", new byte[]{0x48, 0x45, 0x4c, 0x4c, 0x4f, 0x57, 0x4f, 0x52, 0x4c, 0x44, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36}, "/c5dIBxRFq/HNlyRicHnwbt+l915myvBLqpLN01i7uP9vXsg8O275j8jDBCVZrpWUMYo8KnLbLvRiGCc4o5MB7K1DJ5DaKdojWItf57/lse+D81RKU2vRkfkPVq4WCyb+e9eAKEHbx4dZL5EYrNPw2w+pjCZwpnVYodgTxwcHJcAcYDQqN0h8jOyNeYRPmLT".getBytes()));
    }

}
