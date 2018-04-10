package com.sfsctech.authorize.sso.properties;

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

    // 校验协议
    public enum AuthProtocol {
        Http, Rpc
    }

    // 校验方式
    public enum AuthWay {
        Simple, Complex
    }

    // 项目类型
    public enum ItemType {
        BackendSystem,
        FrontendSystem,
        FrameService
    }

    private String domain;
    private String homePage;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
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

        private AuthProtocol protocol = AuthProtocol.Rpc;
        private AuthWay way = AuthWay.Simple;
        private ItemType itemType;

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

        public ItemType getItemType() {
            return itemType;
        }

        public void setItemType(ItemType itemType) {
            this.itemType = itemType;
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
