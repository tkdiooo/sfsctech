package com.sfsctech.support.common.security.des3;

import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.common.util.HexUtil;
import com.sfsctech.support.common.security.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Class Des3
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class Des3 {

    private static final Logger logger = LoggerFactory.getLogger(Des3.class);

    private static Cipher ecipher;
    private static Cipher dcipher;

    static {
        try {
            String alg = "DESede";
            SecretKey secretKey = new SecretKeySpec("*:@7$8!t*:@7$8!t*:@7$8!t".getBytes(StandardCharsets.UTF_8), alg);
            ecipher = Cipher.getInstance(alg);
            dcipher = Cipher.getInstance(alg);

            ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
            dcipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (Exception e) {
            logger.error("Des3加密解密初始化错误", e);
        }
    }

    /**
     * 一般加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encrypt(String data) {
        AssertUtil.isNotBlank(data, "加密信息为空");
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = data.getBytes(StandardCharsets.UTF_8);
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return Base64.encrypt(enc);
        } catch (Exception e) {
            logger.error("明文:" + data + "加密失败", e);
        }
        return "";
    }

    /**
     * 一般解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decrypt(String data) {
        AssertUtil.isNotBlank(data, "解密信息为空");
        try {
            // Decode base64 to get bytes
            byte[] dec = Base64.decrypt(data);
            // Decrypt
            byte[] utf8 = new byte[0];
            if (dec != null) utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            return new String(utf8, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("密文:" + data + "解密失败", e);
        }
        return "";
    }

    private static final String key = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4";

    private static final String Transforms = "DESede";

    /**
     * ECB加密，不要IV，返回Base64格式
     *
     * @param data 明文
     * @return 密文
     */
    public static String encryptECBToBase64(String data) {
        try {
            return Base64.encrypt(encryptECB(key.getBytes(StandardCharsets.UTF_8), data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            logger.error("明文:" + data + "ECB加密失败", e);
        }
        return "";
    }

    /**
     * ECB加密，不要IV，返回Hex格式
     *
     * @param data 明文
     * @return 密文
     */
    public static String encryptECBToHex(String data) {
        return HexUtil.toHexString(encryptECBToBase64(data).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * ECB加密，不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     */
    private static byte[] encryptECB(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Transforms);
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(Transforms + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("明文:" + new String(data) + "ECB加密失败", e);
        }
        return new byte[0];
    }

    /**
     * 根据Base64格式，ECB解密，不要IV
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptECBByBase64(String data) {
        try {
            return new String(decryptECB(key.getBytes(StandardCharsets.UTF_8), Base64.decrypt(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("密文:" + data + "ECB解密失败", e);
        }
        return "";
    }

    /**
     * 根据Base64格式，ECB解密，不要IV
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptECBByHex(String data) {
        try {
            return decryptECBByBase64(new String(HexUtil.fromHexString(data), StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error("密文:" + data + "ECB解密失败", e);
        }
        return "";
    }

    /**
     * ECB解密，不要IV
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     */
    private static byte[] decryptECB(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Transforms);
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(Transforms + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, desKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("明文:" + new String(data) + "ECB解密失败", e);
        }
        return new byte[0];
    }

    private static final byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * CBC加密，返回Base64格式
     *
     * @param data 明文
     * @return 密文
     */
    public static String encryptCBCToBase64(String data) {
        try {
            return Base64.encrypt(encryptCBC(key.getBytes(StandardCharsets.UTF_8), data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            logger.error("明文:" + data + "CBC加密失败", e);
        }
        return "";
    }

    /**
     * CBC加密，返回Hex格式
     *
     * @param data 明文
     * @return 密文
     */
    public static String encryptCBCToHex(String data) {
        try {
            return HexUtil.toHexString(encryptCBCToBase64(data).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error("明文:" + data + "CBC加密失败", e);
        }
        return "";
    }

    /**
     * CBC加密
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     */
    private static byte[] encryptCBC(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Transforms);
            Key desKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(Transforms + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(Des3.keyiv);
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("明文:" + new String(data) + "CBC加密失败", e);
        }
        return new byte[0];
    }

    /**
     * 根据Base64格式，CBC解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptCBCByBase64(String data) {
        try {
            return new String(decryptCBC(key.getBytes(StandardCharsets.UTF_8), Base64.decrypt(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("密文:" + data + "CBC解密失败", e);
        }
        return "";
    }

    /**
     * 根据Hex格式，CBC解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptCBCByHex(String data) {
        try {
            return decryptCBCByBase64(new String(HexUtil.fromHexString(data), StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error("密文:" + data + "CBC解密失败", e);
        }
        return "";
    }

    /**
     * CBC解密
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     */
    private static byte[] decryptCBC(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Transforms);
            Key desKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(Transforms + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(Des3.keyiv);
            cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("密文:" + new String(data) + "CBC解密失败", e);
        }
        return new byte[0];
    }

    public static void main(String[] args) {
        String data = "许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902";

        System.out.println("ECB加密Base64");
        String str3 = encryptECBToBase64(data);
        System.out.println(str3);
        System.out.println("ECB解密Base64");
        String str4 = decryptECBByBase64(str3);
        System.out.println(str4);

        System.out.println("--------------");

        System.out.println("ECB加密Hex");
        str3 = encryptECBToHex(data);
        System.out.println(str3);
        System.out.println("ECB解密Hex");
        str4 = decryptECBByHex(str3);
        System.out.println(str4);

        System.out.println("--------------");

        System.out.println("CBC加密Base64");
        String str5 = encryptCBCToBase64(data);
        System.out.println(str5);
        System.out.println("CBC解密Base64");
        String str6 = decryptCBCByBase64(str5);
        System.out.println(str6);

        System.out.println("--------------");

        System.out.println("CBC加密Hex");
        str5 = encryptCBCToHex(data);
        System.out.println(str5);
        System.out.println("CBC解密Hex");
        str6 = decryptCBCByHex(str5);
        System.out.println(str6);

    }
}
