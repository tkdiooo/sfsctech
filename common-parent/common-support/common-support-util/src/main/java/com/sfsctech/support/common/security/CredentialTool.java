package com.sfsctech.support.common.security;

import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.common.util.CredentialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * Class CredentialTool
 *
 * @author 张麒 2017-12-28.
 * @version Description:
 */
public class CredentialTool {

    private static Logger logger = LoggerFactory.getLogger(CredentialTool.class);

    private String cerPath;
    private String ksPath;
    private String ksPass;
    private KeyStore keyStore;
    private X509Certificate x509Certificate;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String alias;

    public CredentialTool(String cerPath) throws Exception {
        assertCer();
        this.cerPath = cerPath;
        initPublicKey();
    }

    public CredentialTool(String ksPath, String ksPass) throws Exception {
        assertKeyStore();
        this.ksPath = ksPath;
        this.ksPass = ksPass;
        initPrivateKey();
        this.x509Certificate = CredentialUtil.getX509Certificate(keyStore, alias);
        this.publicKey = x509Certificate.getPublicKey();
    }

    private void assertKeyStore() {
        AssertUtil.isNotBlank(ksPath, "密钥库文件路径为空");
        AssertUtil.isNotBlank(ksPass, "密钥库密码为空");
    }

    private void assertCer() {
        AssertUtil.isNotBlank(cerPath, "证书文件路径为空");
    }

    private void initPrivateKey() throws Exception {
        this.keyStore = CredentialUtil.getKeyStore(ksPath, ksPass);
        this.alias = CredentialUtil.getAlias(keyStore);
        this.privateKey = CredentialUtil.getPrikeyByKeyStore(keyStore, ksPass, alias);
    }

    private void initPublicKey() throws Exception {
        this.x509Certificate = CredentialUtil.getX509Certificate(cerPath);
        this.publicKey = x509Certificate.getPublicKey();
    }


    /**
     * 获取密钥库对象（KeyStore）
     *
     * @return KeyStore
     * @throws Exception
     */
    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    /**
     * 根据证书对象（X509Certificate）
     *
     * @return
     */
    public X509Certificate getX509Certificate() {
        return this.x509Certificate;
    }

    /**
     * 获取私钥
     *
     * @return PrivateKey
     */
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    /**
     * 获取公钥
     *
     * @return PublicKey
     */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }


}
