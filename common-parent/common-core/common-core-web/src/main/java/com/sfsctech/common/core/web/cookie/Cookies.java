package com.sfsctech.common.core.web.cookie;

import com.sfsctech.common.support.util.ArrayUtil;
import com.sfsctech.common.support.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class Cookies
 *
 * @author 张麒 2016/5/18.
 * @version Description:
 */
public class Cookies {

    private static final Logger logger = LoggerFactory.getLogger(Cookies.class);

    private HttpServletRequest request;

    private HttpServletResponse response;

    public Cookies(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 根据cookie name获取cookie对象
     *
     * @param name config name
     * @return config
     */
    public Cookie getCookie(String name) {
        logger.info("Cookie Name=" + name);

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            logger.warn("cookies is null");
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            logger.info("cookieName" + i + ":: " + cookies[i].getName());
            if (cookies[i].getName().equalsIgnoreCase(name)) {
                logger.info("config value=" + cookies[i].getValue());
                return cookies[i];
            }
        }
        return null;
    }

    /**
     * 根据cookie name获取cookie value
     *
     * @param name config name
     * @return config value
     */
    public String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        if (null != cookie) return cookie.getValue();
        else return null;
    }


    /**
     * 根据cookie name获取cookie的域
     *
     * @param name config name
     * @return config domain
     */
    public String getCookieDomain(String name) {
        Cookie cookie = getCookie(name);
        if (null != cookie) return cookie.getDomain();
        else return null;
    }

    /**
     * 设置cookie
     *
     * @param name   config name
     * @param value  config value
     * @param config config Config
     */
    public void setCookie(String name, String value, Config config) {
        Cookie cookie = new Cookie(name, value);
        setCookieConfig(cookie, config);
        response.addCookie(cookie);
    }

    /**
     * 设置cookie
     *
     * @param name   config name
     * @param value  config value
     * @param expire config expire
     * @param domain config domain
     */
    public void setCookie(String name, String value, int expire, String... domain) {
        Cookie cookie = new Cookie(name, value);
        if (ArrayUtil.isNotEmpty(domain) && StringUtil.isNotBlank(domain[0])) cookie.setDomain(domain[0]);
        cookie.setPath("/");
        if (expire != 0) {
            cookie.setMaxAge(expire);
        }
        response.addCookie(cookie);
    }

    /**
     * 设置基于session的cookie
     *
     * @param name   config name
     * @param value  config value
     * @param domain config domain
     */
    public void setSessionCookie(String name, String value, String... domain) {
        setCookie(name, value, -1, domain);
    }

    /**
     * 删除cookie
     *
     * @param name   config name
     * @param domain config domain
     */
    public void remove(String name, String... domain) {
        setCookie(name, "", 0, domain);
    }

    /**
     * 清除所有cookie
     */
    public void clear() {
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                remove(cookie.getName(), cookie.getDomain());
            }
        }
    }

    public void setCookieConfig(Cookie cookie, Config config) {
        if (null != config) {
            if (null != config.getMaxAge() && config.getMaxAge() != 0) {
                cookie.setMaxAge(config.getMaxAge());
            }
            if (StringUtil.isNotBlank(config.getDomain())) {
                cookie.setDomain(config.getDomain());
            }
            if (StringUtil.isNotBlank(config.getPath())) {
                cookie.setPath(config.getPath());
            }
            if (StringUtil.isNotBlank(config.getComment())) {
                cookie.setComment(config.getComment());
            }
            if (config.getSecure()) {
                cookie.setSecure(config.getSecure());
            }
            if (config.getHttpOnly()) {
                cookie.setHttpOnly(config.getHttpOnly());
            }
        }
    }
}
