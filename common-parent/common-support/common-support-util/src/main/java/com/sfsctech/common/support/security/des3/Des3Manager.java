package com.sfsctech.common.support.security.des3;

import com.sfsctech.common.support.util.HexUtil;
import com.sun.crypto.provider.SunJCE;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * Class Des3Manager
 *
 * @author 张麒 2016/6/8.
 * @version Description:
 */
public class Des3Manager {

    private Des3Manager() {

    }

    private static Des3Manager instance;

    /**
     * 获取实例
     *
     * @return Des3Manager
     */
    public static Des3Manager getInstance() {
        if (instance == null) {
            synchronized (SecurityManager.class) {
                if (instance == null) {
                    Security.addProvider(new SunJCE());
                    Security.addProvider(new BouncyCastleProvider());// 添加PKCS7Padding支持
                    instance = new Des3Manager();
                }
            }
        }
        return instance;
    }

    private String desStr = "12345678";
    // 默认向量
    private byte[] desInitValue = HexUtil.strToByte(desStr);
    private IvParameterSpec ivspec = new IvParameterSpec(desInitValue);
    private String algorithm = "DESede/CBC/PKCS5Padding";

    /**
     * 动态修改加密向量
     *
     * @param desStr 向量
     */
    public void setDesStr(String desStr) {
        this.desStr = desStr;
        this.desInitValue = HexUtil.strToByte(this.desStr);
        this.ivspec = new IvParameterSpec(this.desInitValue);
    }

    /**
     * 加密字节数组
     *
     * @param data 需加密的字节数组
     * @param key  密钥
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] data, String key) throws Exception {
        Cipher encryptCipher = Cipher.getInstance(algorithm);
        encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(key), ivspec);
        return encryptCipher.doFinal(data);
    }

    /**
     * 加密字符串
     *
     * @param data 需加密的字符串
     * @param key  密钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String data, String key) throws Exception {
        return HexUtil.toHexString(encrypt(data.getBytes(), key));
    }

    /**
     * 解密字节数组
     *
     * @param data 需解密的字节数组
     * @param key  密钥
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] data, String key) throws Exception {
        Cipher decryptCipher = Cipher.getInstance(algorithm);
        decryptCipher.init(Cipher.DECRYPT_MODE, getKey(key), ivspec);
        return decryptCipher.doFinal(data);
    }

    /**
     * 解密字符串
     *
     * @param data 需解密的字符串
     * @param key  密钥
     * @return 解密后的字符串
     */
    public String decrypt(String data, String key) throws Exception {
        return new String(decrypt(HexUtil.fromHexString(data), key));
    }

    private Key getKey(String key) {
        return new SecretKeySpec(key.getBytes(), algorithm);
    }
}
