package com.sfsctech.common.security;

import com.sfsctech.common.security.aes.Aes;
import com.sfsctech.common.security.base64.Base64;
import com.sfsctech.common.security.des3.Des3;
import com.sfsctech.common.security.des3.Des3Manager;
import com.sfsctech.common.security.md5.Md5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class EncrypterTool
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class EncrypterTool {

    private static final Logger logger = LoggerFactory.getLogger(EncrypterTool.class);

    public enum Security {
        Base64, Des3, Des3ECB, DEs3CBC, Md5, Aes
    }

    /**
     * 一般加密
     */
    public static String encrypt(Security security, String info) {
        if (security.equals(Security.Base64)) {
            return Base64.encrypt(info.getBytes());
        } else if (security.equals(Security.Des3)) {
            return Des3.encrypt(info);
        } else if (security.equals(Security.Des3ECB)) {
            return Des3.encryptECB(info);
        } else if (security.equals(Security.DEs3CBC)) {
            return Des3.encryptCBC(info);
        } else if (security.equals(Security.Md5)) {
            return Md5.encode(info);
        } else if (security.equals(Security.Aes)) {
            return Aes.encrypt(info);
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
        } else if (security.equals(Security.Des3ECB)) {
            return Des3.decryptECB(info);
        } else if (security.equals(Security.DEs3CBC)) {
            return Des3.decryptCBC(info);
        } else if (security.equals(Security.Md5)) {
            return info;
        } else if (security.equals(Security.Aes)) {
            return Aes.decrypt(info);
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

    public static void main(String[] args) {
        System.out.println(encrypt(Security.Des3ECB,"hujiong"));
    }
}
