package com.sfsctech.constants;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Class FilterConstants
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class SecurityConstants {

    private static final Pattern pattern = Pattern.compile("^.*?\\.(js|bmp|css|jpg|gif|png|eot|svg|ttf|woff|ico|woff2)$");
    private static final ThreadLocal<Map<String, Boolean>> handler = new ThreadLocal<>();
    private static Set<String> FILTER_EXCLUDES_VALUE;

    static {
        FILTER_EXCLUDES_VALUE = new HashSet<>();
        FILTER_EXCLUDES_VALUE.add("/druid/*");
    }

    public static void addFilterExcludes(String... excludes) {
        FILTER_EXCLUDES_VALUE.addAll(Arrays.asList(excludes));
    }

    public static String getExcludes() {
        return toString(FILTER_EXCLUDES_VALUE, LabelConstants.COMMA);
    }

    public static boolean isExclusion(String requestURI) {
        return isExclusion(requestURI, FILTER_EXCLUDES_VALUE);
    }

    public static boolean isExclusion(String requestURI, Set<String> excludesPattern) {
        // 责任链不为空，并且当前请求的URL已处理过
        if (null != SecurityConstants.handler.get() && SecurityConstants.handler.get().containsKey(requestURI)) {
            return SecurityConstants.handler.get().get(requestURI);
        } else {
            return SecurityConstants.verify(requestURI, excludesPattern);
        }
    }

    private static boolean matches(String url) {
        return pattern.matcher(url).matches();
    }

    private static boolean matches(String pattern, String source) {
        if (pattern != null && source != null) {
            pattern = pattern.trim();
            source = source.trim();
            int start;
            if (pattern.endsWith("*")) {
                start = pattern.length() - 1;
                if (source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
                    return true;
                }
            } else if (pattern.startsWith("*")) {
                start = pattern.length() - 1;
                if (source.length() >= start && source.endsWith(pattern.substring(1))) {
                    return true;
                }
            } else if (pattern.contains("*")) {
                start = pattern.indexOf("*");
                int end = pattern.lastIndexOf("*");
                if (source.startsWith(pattern.substring(0, start)) && source.endsWith(pattern.substring(end + 1))) {
                    return true;
                }
            } else if (pattern.equals(source)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    private static boolean verify(String requestURI, Set<String> excludesPattern) {
        final String url = requestURI;
        if (null == handler.get()) {
            handler.set(new HashMap<>());
        }
        handler.get().put(url, false);
        if (excludesPattern != null && requestURI != null) {
            if (contextPath != null && requestURI.startsWith(contextPath)) {
                requestURI = requestURI.substring(contextPath.length());
                if (!requestURI.startsWith("/")) {
                    requestURI = "/" + requestURI;
                }
            }
            handler.get().put(url, matches(requestURI) || excludesPattern(requestURI));
        }
        return handler.get().get(url);
    }

    private static boolean excludesPattern(String requestURI) {
        Iterator<String> var2 = FILTER_EXCLUDES_VALUE.iterator();
        do {
            if (!var2.hasNext()) return false;
        } while (!matches(var2.next(), requestURI));
        return true;
    }

    // Filter Attribute
    //-------------------------------------------------------------------------------------------
    public static final String FILTER_EXCLUDES_KEY = "exclusions";
    public static String contextPath;


    private static String toString(Set<String> set, String separate) {
        StringBuilder buffer = new StringBuilder();
        set.forEach(s -> {
            if (buffer.length() > 0) {
                buffer.append(separate);
                buffer.append(s);
            } else {
                buffer.append(s);
            }
        });
        return buffer.toString();
    }
}
