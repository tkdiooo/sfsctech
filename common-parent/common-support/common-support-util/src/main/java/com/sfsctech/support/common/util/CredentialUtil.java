package com.sfsctech.support.common.util;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Class CredentialTool
 *
 * @author 张麒 2017-12-28.
 * @version Description:
 */
public class CredentialUtil {

    private static Logger logger = LoggerFactory.getLogger(CredentialUtil.class);

    /**
     * 根据密钥文件路径和密码获取KeyStore对象
     *
     * @param ksPath   密钥库路径
     * @param password 密码
     * @return KeyStore
     * @throws Exception Exception
     */
    public static KeyStore getKeyStore(String ksPath, String password) throws Exception {
        FileInputStream in = new FileInputStream(ksPath);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(in, password.toCharArray());
        in.close();
        return ks;
    }

    /**
     * 根据证书文件路径获取X509Certificate对象
     *
     * @param cerPath 证书路径
     * @return X509Certificate
     * @throws Exception Exception
     */
    public static X509Certificate getX509Certificate(String cerPath) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        InputStream in = new FileInputStream(cerPath);
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(in);
        in.close();
        return certificate;
    }

    /**
     * 获取密钥库第一个别名
     *
     * @param keyStore 密钥库
     * @return Alias
     * @throws Exception Exception
     */
    public static String getAlias(KeyStore keyStore) throws Exception {
        Enumeration<String> aliases = keyStore.aliases();
        String alias = "";
        if (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
            logger.info("密钥别名：{}", alias);
        }
        return alias;
    }


    /**
     * 根据密钥库获取X509Certificate对象（单别名）
     *
     * @param keyStore 密钥库
     * @return X509Certificate
     * @throws Exception Exception
     */
    public static X509Certificate getX509Certificate(KeyStore keyStore) throws Exception {
        return getX509Certificate(keyStore, getAlias(keyStore));
    }

    /**
     * 根据密钥库获取X509Certificate对象（多别名）
     *
     * @param keyStore 密钥库
     * @param alias    别名
     * @return X509Certificate
     * @throws Exception Exception
     */
    public static X509Certificate getX509Certificate(KeyStore keyStore, String alias) throws Exception {
        return (X509Certificate) keyStore.getCertificate(alias);
    }

    /**
     * 根据KeyStore、password、alias获取私钥
     *
     * @param keyStore 密钥库
     * @param password 密码
     * @param alias    别名
     * @return PrivateKey
     * @throws Exception Exception
     */
    public static PrivateKey getPrikeyByKeyStore(KeyStore keyStore, String password, String alias) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        String encoded = new BASE64Encoder().encode(privateKey.getEncoded());
        logger.info("-----BEGIN RSA PRIVATE KEY-----");
        logger.info(encoded);
        logger.info("-----END RSA PRIVATE KEY-----");
        return privateKey;
    }

    /**
     * 根据KeyStore、password获取私钥，alias只能有一个
     *
     * @param keyStore 密钥库
     * @param password 密码
     * @return PrivateKey
     * @throws Exception Exception
     */
    public static PrivateKey getPrikeyByKeyStore(KeyStore keyStore, String password) throws Exception {
        Enumeration<String> aliases = keyStore.aliases();
        String alias = "";
        while (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
            logger.info("密钥别名：{}", alias);
        }
        return getPrikeyByKeyStore(keyStore, password, alias);
    }

    /**
     * 根据密钥库文件路径、password、alias获取私钥
     *
     * @param keyStorePath 密钥库路径
     * @param password     密码
     * @param alias        别名
     * @return PrivateKey
     */
    public static PrivateKey getPrikeyByKeyStore(String keyStorePath, String password, String alias) throws Exception {
        return getPrikeyByKeyStore(getKeyStore(keyStorePath, password), password, alias);
    }

    /**
     * 根据密钥库文件路径、password获取私钥，alias只能有一个
     *
     * @param keyStorePath 密钥库路径
     * @param password     密码
     * @return PrivateKey
     * @throws Exception
     */
    public static PrivateKey getPrikeyByKeyStore(String keyStorePath, String password) throws Exception {
        return getPrikeyByKeyStore(getKeyStore(keyStorePath, password), password);
    }

    /**
     * 由证书获得公钥
     *
     * @param cerPath 证书路径
     * @return PublicKey
     * @throws Exception
     */
    public static PublicKey getPubKeyByCer(String cerPath) throws Exception {
        return getX509Certificate(cerPath).getPublicKey();
    }

    /**
     * 由密钥库生成签名
     *
     * @param data         签名信息
     * @param keyStorePath 密钥库路径
     * @param password     密码
     * @param alias        别名
     * @param cerPath      证书路径
     * @return 签名对象
     */
    public static byte[] sign(byte[] data, String keyStorePath, String password, String alias, String cerPath) throws Exception {
        //获得证书
        X509Certificate x509certificate = getX509Certificate(cerPath);
        //构建签名
        Signature signature = Signature.getInstance(x509certificate.getSigAlgName());
        PrivateKey privateKey = getPrikeyByKeyStore(keyStorePath, password, alias);
        //初始化签名
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 验证签名
     *
     * @param data    签名信息
     * @param sign    签名对象
     * @param cerPath 证书路径
     * @return boolean
     */
    public static boolean verify(byte[] data, byte[] sign, String cerPath) throws Exception {
        X509Certificate x509certificate = getX509Certificate(cerPath);
        //构建签名
        Signature signature = Signature.getInstance(x509certificate.getSigAlgName());
        signature.initVerify(x509certificate);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * 通过证书加密数据
     *
     * @param data    明文数据
     * @param cerPath 证书路径
     * @return 密文
     */
    public static byte[] encryptByCer(byte[] data, String cerPath) throws Exception {
        // 取得公钥
        PublicKey publicKey = getPubKeyByCer(cerPath);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);

    }


    public static void main(String[] args) throws Exception {

        String keyStorePath = "d:/gus.keystore";
        String cerPath = "d:/gus.cer";
        String password = "hello!@#";
        String alias = "www.gus.com";

        PrivateKey prikey = getPrikeyByKeyStore(keyStorePath, password, alias);
        System.err.println("私钥：\n" + prikey);

        PublicKey pubkey = getPubKeyByCer(cerPath);
        System.err.println("公钥：\n" + pubkey);

        byte[] sign = sign("待签名的".getBytes(), keyStorePath, password, alias, cerPath);

        System.out.println(Hex.encodeHex(sign));

        //传来的数据最好加密
        System.out.println(verify("待签名的".getBytes(), sign, cerPath));
    }

}
