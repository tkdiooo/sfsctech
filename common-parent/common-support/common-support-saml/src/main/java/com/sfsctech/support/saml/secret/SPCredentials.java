package com.sfsctech.support.saml.secret;

import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.support.common.security.CredentialTool;
import com.sfsctech.support.common.util.ThrowableUtil;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.KeyStoreCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SpCredential
 *
 * @author 张麒 2019-12-4.
 * @version Description:
 */
public class SPCredentials {

    private static Logger logger = LoggerFactory.getLogger(SPCredentials.class);

    private Credential credential;

    public SPCredentials(String keyStore, String keyPass) {
        try {
            CredentialTool credentialTool = new CredentialTool(keyStore, keyPass);
            Map<String, String> passwordMap = new HashMap<>();
            passwordMap.put(credentialTool.getAlias(), keyPass);
            KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(credentialTool.getKeyStore(), passwordMap);

            Criterion criterion = new EntityIdCriterion(credentialTool.getAlias());
            CriteriaSet criteriaSet = new CriteriaSet();
            criteriaSet.add(criterion);

            credential = resolver.resolveSingle(criteriaSet);
        } catch (Exception e) {
            throw new BizException(ThrowableUtil.getRootMessage(e), e);
        }
    }

    public Credential getCredential() {
        return credential;
    }

}
