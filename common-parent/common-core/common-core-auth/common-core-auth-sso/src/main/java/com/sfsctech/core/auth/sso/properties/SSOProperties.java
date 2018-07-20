package com.sfsctech.core.auth.sso.properties;

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

    public enum SessionKeep {
        Cookie, Header
    }

    // 校验方式
    public enum AuthWay {
        Simple, Complex, Local
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

    public SSOProperties() {
        this.auth = new SSOProperties.Authentication();
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

    public static class Authentication {

        private AuthWay way = AuthWay.Simple;
        private SessionKeep sessionKeep = SessionKeep.Cookie;
        private ItemType itemType;

        Authentication() {
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

        public SessionKeep getSessionKeep() {
            return sessionKeep;
        }

        public void setSessionKeep(SessionKeep sessionKeep) {
            this.sessionKeep = sessionKeep;
        }
    }
}
