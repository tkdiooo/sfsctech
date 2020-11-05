package com.sfsctech.support.saml.properties;

import com.google.common.collect.Maps;
import com.sfsctech.support.saml.secret.SPCredentials;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class AuthnRequestPropertyies
 *
 * @author 张麒 2019-12-3.
 * @version Description:
 */
@Component
@RefreshScope
@ConfigurationProperties(
        prefix = "saml.sp"
)
public class SpPropertyies {

    private Map<String, SpConfig> config = Maps.newHashMap();

    public Map<String, SpConfig> getConfig() {
        return config;
    }

    public void setConfig(Map<String, SpConfig> config) {
        this.config = config;
    }

    /**
     * Session保持类型
     */
    public enum AuthnContextComparisonTypeEnumeration {
        exact, minimum, maximum, better
    }

    public static class SpConfig {

        private String IPDSSODestination;
        private String protocolBinding = SAMLConstants.SAML2_POST_BINDING_URI;
        private String assertionConsumerServiceUrl;
        private String entityId;
        private String cerPath;
        private String keyPath;
        private String certificateTag;
        private String encryptionSeparated;
        private SPCredentials spCredential;

        private String attributeName;
        private String redirectUrl;
        private boolean verifyDestinationLifetime;
        private boolean verifyAssertionSignature;
        private boolean isRequestedAuthnContext;

        public String getProtocolBinding() {
            return protocolBinding;
        }

        public void setProtocolBinding(String protocolBinding) {
            this.protocolBinding = protocolBinding;
        }


        public String getAssertionConsumerServiceUrl() {
            return assertionConsumerServiceUrl;
        }

        public void setAssertionConsumerServiceUrl(String assertionConsumerServiceUrl) {
            this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
        }

        public SPCredentials getSpCredential() {
            if (null == spCredential) {
                synchronized (this) {
                    try {
                        spCredential = new SPCredentials(this.cerPath, this.keyPath);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return spCredential;
        }

        public String getIPDSSODestination() {
            return IPDSSODestination;
        }

        public void setIPDSSODestination(String IPDSSODestination) {
            this.IPDSSODestination = IPDSSODestination;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getCerPath() {
            return cerPath;
        }

        public void setCerPath(String cerPath) {
            this.cerPath = cerPath;
        }

        public String getKeyPath() {
            return keyPath;
        }

        public void setKeyPath(String keyPath) {
            this.keyPath = keyPath;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public boolean isVerifyDestinationLifetime() {
            return verifyDestinationLifetime;
        }

        public void setVerifyDestinationLifetime(boolean verifyDestinationLifetime) {
            this.verifyDestinationLifetime = verifyDestinationLifetime;
        }

        public boolean isVerifyAssertionSignature() {
            return verifyAssertionSignature;
        }

        public void setVerifyAssertionSignature(boolean verifyAssertionSignature) {
            this.verifyAssertionSignature = verifyAssertionSignature;
        }

        public String getCertificateTag() {
            return certificateTag;
        }

        public void setCertificateTag(String certificateTag) {
            this.certificateTag = certificateTag;
        }

        public String getEncryptionSeparated() {
            return encryptionSeparated;
        }

        public void setEncryptionSeparated(String encryptionSeparated) {
            this.encryptionSeparated = encryptionSeparated;
        }

        public boolean isRequestedAuthnContext() {
            return isRequestedAuthnContext;
        }

        public void setRequestedAuthnContext(boolean requestedAuthnContext) {
            isRequestedAuthnContext = requestedAuthnContext;
        }
    }
}
