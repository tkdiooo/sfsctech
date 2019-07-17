package com.sfsctech.support.common.security;

import com.sfsctech.support.common.security.aes.Aes;
import com.sfsctech.support.common.security.des3.Des3Manager;
import com.sfsctech.support.common.security.md5.Md5;
import com.sfsctech.support.common.security.base64.Base64;
import com.sfsctech.support.common.security.des3.Des3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Class EncrypterTool
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class EncrypterTool {

    private static final Logger logger = LoggerFactory.getLogger(EncrypterTool.class);

    public enum Security {
        Base64, Des3, Des3ECBBase64, Des3ECBHex, Des3CBCBase64, Des3CBCHex, Md5, Aes, AesCBC
    }

    /**
     * 一般加密
     */
    public static String encrypt(Security security, String info) {
        if (security.equals(Security.Base64)) {
            return Base64.encrypt(info.getBytes());
        } else if (security.equals(Security.Des3)) {
            return Des3.encrypt(info);
        } else if (security.equals(Security.Des3ECBBase64)) {
            return Des3.encryptECBToBase64(info);
        } else if (security.equals(Security.Des3ECBHex)) {
            return Des3.encryptECBToHex(info);
        } else if (security.equals(Security.Des3CBCBase64)) {
            return Des3.encryptCBCToBase64(info);
        } else if (security.equals(Security.Des3CBCHex)) {
            return Des3.encryptCBCToHex(info);
        } else if (security.equals(Security.Md5)) {
            return Md5.encode(info);
        } else if (security.equals(Security.Aes)) {
            return Aes.encrypt(info);
        } else if (security.equals(Security.AesCBC)) {
            return Aes.encryptCBC(info);
        }
        return "";
    }

    /**
     * 一般解密
     */
    public static String decrypt(Security security, String info) {
        if (security.equals(Security.Base64)) {
            return new String(Base64.decrypt(info));
        } else if (security.equals(Security.Des3)) {
            return Des3.decrypt(info);
        } else if (security.equals(Security.Des3ECBBase64)) {
            return Des3.decryptECBByBase64(info);
        } else if (security.equals(Security.Des3ECBHex)) {
            return Des3.decryptECBByHex(info);
        } else if (security.equals(Security.Des3CBCBase64)) {
            return Des3.decryptCBCByBase64(info);
        } else if (security.equals(Security.Des3CBCHex)) {
            return Des3.decryptCBCByHex(info);
        } else if (security.equals(Security.Md5)) {
            return info;
        } else if (security.equals(Security.Aes)) {
            return Aes.decrypt(info);
        } else if (security.equals(Security.AesCBC)) {
            return Aes.decryptCBC(info);
        }
        return "";
    }

    /**
     * 解密字符串
     *
     * @param data 需解密的字符串
     * @param key  24位长度密钥
     * @return 解密后的字符串, 异常返回空
     */
    public static String decrypt(String data, String key) {
        try {
            logger.debug("待解密的字符串:............." + data);
            logger.debug("待解密的字符串密钥:..............." + key);
            return Des3Manager.getInstance().decrypt(data, key);
        } catch (Exception ex) {
            logger.error("3DES解密出错:" + ex.getMessage() + " key:" + key, ex);
        }
        return null;
    }

    /**
     * 加密字符串
     *
     * @param data 待加密的字符串
     * @param key  24位长度密钥
     * @return 3DES加密后的字符串
     */
    public static String encrypt(String data, String key) {
        try {
            logger.debug("待加密的字符串:............." + data);
            logger.debug("待加密的字符串密钥:..............." + key);
            return Des3Manager.getInstance().encrypt(data, key);
        } catch (Exception ex) {
            logger.error("3DES加密出错:" + ex.getMessage() + " key:" + key, ex);
            return null;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String data = EncrypterTool.encrypt(Security.Des3ECBHex, "待加密的字符串@#@#SSS23s2433!*(");
//        System.out.println(data);
//        new Thread(() -> {
//            while (true) {
//                System.out.println(1);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(2);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(3);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(4);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(5);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        try {
////            String key = URLEncoder.encode(encrypt(Security.AesCBC, "0000792773"), "UTF-8");
////            System.out.println(key);
////            System.out.println(decrypt(Security.AesCBC, URLDecoder.decode(key, "UTF-8")));
////            String key1 = URLEncoder.encode(encrypt(Security.AesCBC, "index"), "UTF-8");
////            System.out.println(key1);
//            System.out.println(URLEncoder.encode(encrypt(Security.AesCBC, "http://m.fsgplus.com/home?v=1530436188891"), "UTF-8"));
//            System.out.println(decrypt(Security.AesCBC,"6xvDUkUPdlVnwQIuK33bfUDNunlluRuUKLAwSW9wV0mjVo7FQ/nEcdIBTBvJA+aH"));
//            System.out.println(URLDecoder.decode("81B6FDA9FB15F0CAE4BB52B5DEDDAAAEAD910C5980A7C16EF07656647048966B1D0159A5556ED080A436898EDE53B00E", "UTF-8"));
////            System.out.println(decrypt(Security.AesCBC, ));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        System.out.println(encrypt(Security.Aes, "weishiyao"));
//        System.out.println(URLEncoder.encode("06646650B61CF129F01DCE637D319B27CE35BF37B9F66A5802B4DD3EF6A4BE5DAEF728032473784AC0E8658C92D4804B","UTF-8"));;
    }
}
