package com.sfsctech.common.security.rsa;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Class RSA
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class RSA {

    public static final Map<String, KeyPairModel> LOCAL_KEYPAIRMODEL = new HashMap<>();

    /**
     * ASCII码转BCD码
     */
    private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    private static byte asc_to_bcd(byte asc) {
        byte bcd;
        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * BCD转字符串
     */
    private static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    private static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str;
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    private static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * 获取新的公钥和私钥
     */
    public static KeyPairModel getKeys() {
        KeyPairModel keys = new KeyPairModel();
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            keys.setPublicKey(publicKey);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keys.setPrivateKey(privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keys;
    }

    /**
     * 获取RSAModel，加密数据
     */
    public static RSAModel getRSAModel(String data) {
        RSAModel model = new RSAModel();
        KeyPairModel keyPair = getKeys();
        RSAPublicKey publicKey = keyPair.getPublicKey();
        RSAPrivateKey privateKey = keyPair.getPrivateKey();
        // 模
        BigInteger modulus = publicKey.getModulus();
        // 公钥指数
        BigInteger public_exponent = publicKey.getPublicExponent();
        // 使用模和公钥指数生成公钥
        RSAPublicKey pubKey = getPublicKey(modulus, public_exponent);
        // 生成公钥加密数据
        String mi = encryptByPublicKey(data, pubKey);
        // 私钥指数
        BigInteger private_exponent = privateKey.getPrivateExponent();
        model.setModulus(modulus);
        model.setPassword(mi);
        model.setPrivateExponent(private_exponent);
        model.setPublicExponent(public_exponent);
        return model;
    }

    /**
     * 使用模和指数生成RSA公钥
     * <pre>
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     * </pre>
     *
     * @param modulus  模
     * @param exponent 指数
     * @return RSAPublicKey
     */
    public static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用模和指数生成RSA私钥
     * <pre>
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     * </pre>
     *
     * @param modulus  模
     * @param exponent 指数
     * @return RSAPrivateKey
     */
    public static RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) {
        String mi = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 模长
            int key_len = publicKey.getModulus().bitLength() / 8;
            // 加密数据长度 <= 模长-11
            String[] datas = splitString(data, key_len - 11);
            // 如果明文长度大于模长-11则要分组加密
            for (String s : datas) {
                mi += bcd2Str(cipher.doFinal(s.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mi;
    }

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) {
        String ming = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 模长
            int key_len = privateKey.getModulus().bitLength() / 8;
            byte[] bytes = data.getBytes();
            byte[] bcd = ASCII_To_BCD(bytes, bytes.length);

            // 如果密文长度大于模长则要分组解密

            byte[][] arrays = splitArray(bcd, key_len);
            for (byte[] arr : arrays) {
                ming += new String(cipher.doFinal(arr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ming;
    }

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String data, BigInteger modulus, BigInteger private_exponent) {
        try {
            RSAPrivateKey priKey = getPrivateKey(modulus, private_exponent);
            // 解密后的明文
            return decryptByPrivateKey(data, priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 公钥加密
     *
     * @param pk   加密的公钥
     * @param data 待加密的明文数据
     * @return 加密后的数据 byte[]
     */
    public static byte[] simpleEncrypt(PublicKey pk, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            // 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            int blockSize = cipher.getBlockSize();
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
                    : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i
                            * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i
                            * blockSize, raw, i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。

                i++;
            }
            return raw;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param pk  解密的私钥
     * @param raw 已经加密的数据
     * @return 解密后的明文 byte[]
     */
    public static byte[] simpleDecrypt(PrivateKey pk, byte[] raw) {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
