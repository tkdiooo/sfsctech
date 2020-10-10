package com.sfsctech.support.saml.secret;

import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.support.common.util.CredentialUtil;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.opensaml.security.x509.BasicX509Credential;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Class SpCredential
 *
 * @author 张麒 2019-12-4.
 * @version Description:
 */
public class SPCredentials {

    private BasicX509Credential basicX509Credential;

    public SPCredentials(String cerPath, String keyPath) {
        try {
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(FileUtil.readFileToByteArray(new File(keyPath)));
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
            X509Certificate certificate = CredentialUtil.getX509Certificate(cerPath);
            basicX509Credential = new BasicX509Credential(certificate, privateKey);
        } catch (Exception e) {
            throw new BizException(ThrowableUtil.getRootMessage(e), e);
        }
    }

    public BasicX509Credential getBasicX509Credential() {
        return basicX509Credential;
    }
}
