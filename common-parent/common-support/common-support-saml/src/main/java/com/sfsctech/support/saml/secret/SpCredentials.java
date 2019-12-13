package com.sfsctech.support.saml.secret;

import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.support.common.security.CredentialTool;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.CredentialSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class SpCredential
 *
 * @author 张麒 2019-12-4.
 * @version Description:
 */
public class SpCredentials {

    private static Logger logger = LoggerFactory.getLogger(SpCredentials.class);

    private BasicCredential basicCredential;

    public SpCredentials(String keyStore, String keyPass) {
        try {
            CredentialTool credentialTool = new CredentialTool(keyStore, keyPass);
            basicCredential = CredentialSupport.getSimpleCredential(credentialTool.getX509Certificate(), credentialTool.getPrivateKey());
        } catch (Exception e) {
            throw new BizException(ThrowableUtil.getRootMessage(e), e);
        }
    }

    public BasicCredential getBasicCredential() {
        return basicCredential;
    }

}
