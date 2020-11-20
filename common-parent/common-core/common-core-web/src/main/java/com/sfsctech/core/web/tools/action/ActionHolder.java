package com.sfsctech.core.web.tools.action;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Class ActionHolder
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class ActionHolder {

    private static final ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return threadLocal.get();
    }

    public static void setRequest(final HttpServletRequest request) {
        threadLocal.set(request);
    }

    public static void clear() {
        threadLocal.set(null);
    }

    public static Locale getLocale() {
        return null != threadLocal.get() ? threadLocal.get().getLocale() : LocaleContextHolder.getLocale();
    }
}
