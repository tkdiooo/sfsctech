package com.sfsctech.common.security;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Class CredentialTool
 *
 * @author 张麒 2017-12-28.
 * @version Description:
 */
public class CredentialTool {

    /**
     * 由密钥库获得私钥
     *
     * @param keyStorePath 密钥库路径
     * @param password     密码
     * @param alias        别名
     * @return PrivateKey
     */
    public static PrivateKey getPrikeyByKeyStore(String keyStorePath, String password, String alias) throws Exception {
        //实例化密钥库
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream is = new FileInputStream(keyStorePath);
        //加载密钥库
        ks.load(is, password.toCharArray());
        is.close();
        return (PrivateKey) ks.getKey(alias, password.toCharArray());
    }

    /**
     * 由证书获得公钥
     *
     * @param cerPath 证书路径
     * @return PublicKey
     */
    public static PublicKey getPubKeyByCer(String cerPath) throws Exception {
        //实例化证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cerPath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate.getPublicKey();
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
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cerPath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        X509Certificate x509certificate = (X509Certificate) certificate;
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
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cerPath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        X509Certificate x509certificate = (X509Certificate) certificate;
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
