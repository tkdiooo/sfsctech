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
    private static Set<String> CSRF_EXCLUDES_VALUE;

    static {
        FILTER_EXCLUDES_VALUE = new HashSet<>();
        FILTER_EXCLUDES_VALUE.add("/druid/*");
        FILTER_EXCLUDES_VALUE.add(SecurityConstants.ERROR_PATH + LabelConstants.SLASH_STAR);
        CSRF_EXCLUDES_VALUE = new HashSet<>();
        CSRF_EXCLUDES_VALUE.add("/druid/*");
        CSRF_EXCLUDES_VALUE.add(SecurityConstants.ERROR_PATH + LabelConstants.SLASH_DOUBLE_STAR);
    }

    public static void addFilterExcludes(String... excludes) {
        FILTER_EXCLUDES_VALUE.addAll(Arrays.asList(excludes));
    }

    public static void addCSRFExcludes(String... excludes) {
        CSRF_EXCLUDES_VALUE.addAll(Arrays.asList(excludes));
    }

    public static String getFilterExcludes() {
        return toString(FILTER_EXCLUDES_VALUE, LabelConstants.COMMA);
    }

    public static String[] getCSRFExcludes() {
        return CSRF_EXCLUDES_VALUE.toArray(new String[]{});
    }

    /**
     * 通过责任链处理的校验
     *
     * @param requestURI
     * @return
     */
    public static boolean isExclusion(String requestURI) {
        // 责任链不为空，并且当前请求的URL已处理过
        if (null != SecurityConstants.handler.get() && SecurityConstants.handler.get().containsKey(requestURI)) {
            return SecurityConstants.handler.get().get(requestURI);
        } else {
            return SecurityConstants.verify(requestURI);
        }
    }

    /**
     * 不通过责任链处理的校验
     *
     * @param requestURI
     * @param excludes
     * @return
     */
    public static boolean isExclusion(String requestURI, Set<String> excludes) {
        return SecurityConstants.verify(requestURI, excludes);
    }

    public static boolean matches(String url) {
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

    private static boolean verify(String requestURI) {
        final String url = requestURI;
        if (null == handler.get()) {
            handler.set(new HashMap<>());
        }
        handler.get().put(url, false);
        if (FILTER_EXCLUDES_VALUE != null && requestURI != null) {
            requestURI = formatRequestURI(requestURI);
            handler.get().put(url, matches(requestURI) || excludesPattern(requestURI, FILTER_EXCLUDES_VALUE));
        }
        return handler.get().get(url);
    }

    private static boolean verify(String requestURI, Set<String> excludes) {
        if (excludes != null && requestURI != null) {
            requestURI = formatRequestURI(requestURI);
            return matches(requestURI) || excludesPattern(requestURI, excludes);
        }
        return false;
    }

    private static String formatRequestURI(String requestURI) {
        if (CONTEXT_PATH != null && requestURI.startsWith(CONTEXT_PATH)) {
            if (requestURI.endsWith(LabelConstants.FORWARD_SLASH) && requestURI.length() > CONTEXT_PATH.length() + 1) {
                requestURI = requestURI.substring(CONTEXT_PATH.length(), requestURI.length() - 1);
            } else {
                requestURI = requestURI.substring(CONTEXT_PATH.length());
            }
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }
        return requestURI;
    }

    private static boolean excludesPattern(String requestURI, Set<String> excludes) {
        Iterator<String> var2 = excludes.iterator();
        do {
            if (!var2.hasNext()) return false;
        } while (!matches(var2.next(), requestURI));
        return true;
    }

    // Filter Attribute
    //-------------------------------------------------------------------------------------------
    public static final String FILTER_EXCLUDES_KEY = "exclusions";
    public static final String ERROR_PATH = "/error";
    public static final String SERVICE_SOA = "soa";
    public static String CONTEXT_PATH;
    public static String SERVER_SUFFIX;
    public static String SERVER_STATIC_PATH;
    public static String SERVICE_AUTHENTICATION;


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
