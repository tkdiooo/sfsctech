package com.sfsctech.support.saml.properties;

import com.sfsctech.support.saml.secret.SpCredentials;

/**
 * Class AuthnRequestPropertyies
 *
 * @author 张麒 2019-12-3.
 * @version Description:
 */
public class SpConfig {

    private String idpSsoUrl;
    private String protocolBinding;
    private String assertionConsumerServiceUrl;
    private String spEntityId;
    private String keyStore;
    private String keyPass;
    private SpCredentials spCredential;

    public String getIdpSsoUrl() {
        return idpSsoUrl;
    }

    public void setIdpSsoUrl(String idpSsoUrl) {
        this.idpSsoUrl = idpSsoUrl;
    }

    public String getProtocolBinding() {
        return protocolBinding;
    }

    public void setProtocolBinding(String protocolBinding) {
        this.protocolBinding = protocolBinding;
    }

    public String getSpEntityId() {
        return spEntityId;
    }

    public void setSpEntityId(String spEntityId) {
        this.spEntityId = spEntityId;
    }

    public String getAssertionConsumerServiceUrl() {
        return assertionConsumerServiceUrl;
    }

    public void setAssertionConsumerServiceUrl(String assertionConsumerServiceUrl) {
        this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
    }

    public SpCredentials getSpCredential() {
        if (null == spCredential) {
            synchronized (this) {
                try {
                    spCredential = new SpCredentials(this.keyStore, this.keyPass);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return spCredential;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStore() {
        return this.keyStore;
    }

    public String getKeyPass() {
        return keyPass;
    }

    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }
}
