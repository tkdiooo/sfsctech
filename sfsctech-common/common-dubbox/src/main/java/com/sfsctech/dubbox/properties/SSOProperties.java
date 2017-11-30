package com.sfsctech.dubbox.properties;

import com.sfsctech.constants.SSOConstants.AuthProtocol;
import com.sfsctech.constants.SSOConstants.AuthWay;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class SSOProperties
 *
 * @author 张麒 2017/9/1.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "website.sso"
)
public class SSOProperties {
    private String portalUrl;
    private String loginUrl;
    private String logoutUrl;
    private String checkUrl;
    private String registerUrl;
    private String forgetUrl;

    private final SSOProperties.Authentication auth;

    private final SSOProperties.Reference reference;

    public SSOProperties() {
        this.auth = new SSOProperties.Authentication();
        this.reference = new SSOProperties.Reference();
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getCheckUrl() {
        return checkUrl;
    }

    public void setCheckUrl(String checkUrl) {
        this.checkUrl = checkUrl;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getForgetUrl() {
        return forgetUrl;
    }

    public void setForgetUrl(String forgetUrl) {
        this.forgetUrl = forgetUrl;
    }

    public Authentication getAuth() {
        return auth;
    }

    public Reference getReference() {
        return reference;
    }


    public static class Authentication {

        private AuthProtocol protocol;
        private AuthWay way;

        Authentication() {
        }

        public AuthProtocol getProtocol() {
            return protocol;
        }

        public void setProtocol(AuthProtocol protocol) {
            this.protocol = protocol;
        }

        public AuthWay getWay() {
            return way;
        }

        public void setWay(AuthWay way) {
            this.way = way;
        }
    }

    public static class Reference {
        private boolean lazy;
        private String version;
        private Integer timeout;

        Reference() {
        }

        public boolean isLazy() {
            return lazy;
        }

        public void setLazy(boolean lazy) {
            this.lazy = lazy;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }
    }
}
