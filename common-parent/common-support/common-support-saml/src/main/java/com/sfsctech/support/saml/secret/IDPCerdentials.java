package com.sfsctech.support.saml.secret;


import com.sfsctech.core.base.ex.BizException;
import com.sfsctech.support.common.security.CredentialTool;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class IDPCerdentials
 *
 * @author 张麒 2019-12-27.
 * @version Description:
 */
public class IDPCerdentials {

    private static Logger logger = LoggerFactory.getLogger(IDPCerdentials.class);

    private Credential credential;

    public IDPCerdentials(String keyStore, String keyPass) {
        try {
            CredentialTool credentialTool = new CredentialTool(keyStore, keyPass);
            credential = CredentialSupport.getSimpleCredential(credentialTool.getPublicKey(), credentialTool.getPrivateKey());
        } catch (Exception e) {
            throw new BizException(ThrowableUtil.getRootMessage(e), e);
        }
    }

    public Credential getCredential() {
        return credential;
    }
}
