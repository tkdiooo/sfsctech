package com.sfsctech.core.web.cookie;

import com.sfsctech.core.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class CookiesHelp
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class CookieHelper {

    public static CookieHelper getInstance(HttpServletRequest request, HttpServletResponse response) {
        return new CookieHelper(request, response, SpringContextUtil.getBean(Config.class));
    }

    private final Logger logger = LoggerFactory.getLogger(CookieHelper.class);

    private Cookies cookies;
    private Config config;

    private CookieHelper(HttpServletRequest request, HttpServletResponse response, Config config) {
        this.cookies = new Cookies(request, response);
        this.config = config;
    }

    public void setCookie(String key, String value) {
        cookies.setCookie(key, value, config);
    }

    public void setCookie(String key, String value, int expire) {
        cookies.setCookie(key, value, expire, config.getDomain());
    }

    public void setSessionCookie(String key, String value) {
        cookies.setSessionCookie(key, value, config.getDomain());
    }

    public Cookie getCookie(String key) {
        return cookies.getCookie(key);
    }

    public String getCookieValue(String key) {
        return cookies.getCookieValue(key);
    }

    public void clearCookie(String key) {
        cookies.remove(key, config.getDomain());
    }

    public Config getConfig() {
        return config;
    }

    public Cookies getCookies() {
        return cookies;
    }
}
