package com.sfsctech.common.support.security.des3;

import com.sfsctech.common.support.security.EncrypterTool;
import com.sfsctech.common.support.security.base64.Base64;
import com.sfsctech.common.support.util.AssertUtil;
import com.sfsctech.common.support.util.HexUtil;
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
            SecretKey secretKey = new SecretKeySpec("*:@7$8!t*:@7$8!t*:@7$8!t".getBytes(), alg);
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
            byte[] utf8 = data.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return Base64.encrypt(enc);
        } catch (Exception e) {
            logger.error("明文：" + data + "加密失败", e);
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
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            logger.error("密文：" + data + "解密失败", e);
        }
        return "";
    }

    private static final String key = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4";

    /**
     * ECB加密,不要IV
     *
     * @param data 明文
     * @return 密文
     */
    public static String encryptECB(String data) {
        try {
            return HexUtil.toHexString(encryptECB(new BASE64Decoder().decodeBuffer(key), data.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("明文：" + data + "ECB加密失败", e);
        }
        return "";
    }

    /**
     * ECB加密,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     */
    public static byte[] encryptECB(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("明文：" + new String(data) + "ECB加密失败", e);
        }
        return new byte[0];
    }

    /**
     * ECB解密,不要IV
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptECB(String data) {
        try {
            return new String(decryptECB(new BASE64Decoder().decodeBuffer(key), HexUtil.fromHexString(data)), "UTF-8");
        } catch (Exception e) {
            logger.error("密文：" + data + "ECB解密失败", e);
        }
        return "";
    }

    /**
     * ECB解密,不要IV
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     */
    public static byte[] decryptECB(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, desKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("明文：" + new String(data) + "ECB解密失败", e);
        }
        return new byte[0];
    }

    private static final byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * CBC加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encryptCBC(String data) {
        try {
            return HexUtil.toHexString(encryptCBC(new BASE64Decoder().decodeBuffer(key), keyiv, data.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("明文：" + data + "CBC加密失败", e);
        }
        return "";
    }

    /**
     * CBC加密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  明文
     * @return Base64编码的密文
     */
    public static byte[] encryptCBC(byte[] key, byte[] keyiv, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("明文：" + new String(data) + "CBC加密失败", e);
        }
        return new byte[0];
    }

    /**
     * CBC解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptCBC(String data) {
        try {
            return new String(decryptCBC(new BASE64Decoder().decodeBuffer(key), keyiv, HexUtil.fromHexString(data)), "UTF-8");
        } catch (Exception e) {
            logger.error("密文：" + data + "CBC解密失败", e);
        }
        return "";
    }

    /**
     * CBC解密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  Base64编码的密文
     * @return 明文
     */
    public static byte[] decryptCBC(byte[] key, byte[] keyiv, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("密文：" + new String(data) + "CBC解密失败", e);
        }
        return new byte[0];
    }

    public static void main(String[] args) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4");
        byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};

        byte[] data = "许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902".getBytes("UTF-8");

        System.out.println("ECB加密解密");
        byte[] str3 = encryptECB(key, data);
        byte[] str4 = decryptECB(key, str3);
        System.out.println(HexUtil.toHexString(str3));
        System.out.println(new String(str4, "UTF-8"));

        System.out.println("--------------");

        System.out.println("CBC加密解密");
        byte[] str5 = encryptCBC(key, keyiv, data);
        byte[] str6 = decryptCBC(key, keyiv, str5);
        System.out.println(new BASE64Encoder().encode(str5));
        System.out.println(new String(str6, "UTF-8"));

        System.out.println("--------------" + HexUtil.getEncryptKey());
        System.out.println(EncrypterTool.encrypt("许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902", HexUtil.getEncryptKey()));

        System.out.println("--------------");
        System.out.println(Des3.encrypt("许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902"));
        System.out.println(Des3.decrypt("QPq6wZQifcIBSaafOIZ_EQRsmLBbUKyhNRNTDo2nLlHfopXQa4uUTJvoCOLKmhuFN0TjM-23hLovC7pznSWwGE85T8TqT2mS"));

        System.out.println("--------------");
        System.out.println(Des3.encryptECB("许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902"));
        System.out.println(Des3.decryptECB("2E24BB2FAF0A83FA0BE241257A81E3B38D1353792B14498AF94ADF14606CCDBFEEC9202069213BD8A6A75E978AA7F64336D07EEE76C11B7AE5269179E09F60059D71635B3BE5A155"));

        System.out.println("--------------");
        System.out.println(Des3.encryptCBC("许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902"));
        System.out.println(Des3.decryptCBC("65C6F4AE34441E2F4CE633C15A8DC08C97576ECAFAB1E9766676F21B4FF258C7C0EE7E2B965AD934007BFAF89F111DF8E5550A08BC2693869760DA09888DA297E8035D13FF6FCC24"));
    }
}
