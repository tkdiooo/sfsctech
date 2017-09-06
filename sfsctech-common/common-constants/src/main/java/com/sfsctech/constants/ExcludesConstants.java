package com.sfsctech.constants;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Class FilterConstants
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class ExcludesConstants {

    private static final Pattern pattern = Pattern.compile("^.*?\\.(js|bmp|css|jpg|gif|png|eot|svg|ttf|woff|ico|woff2)$");
    private static final ThreadLocal<Map<String, Boolean>> STATIC_RESOURCE = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Boolean>> REQUEST_MAPPING = new ThreadLocal<>();
    // 默认不过滤/、/druid/*、/error/*
    public static Set<String> SESSION_EXCLUDES_PATTERNS;
    // 默认不过滤/、/druid/*、/error/**
    public static Set<String> INTERCEPT_EXCLUDES_PATTERNS;

    static {
        SESSION_EXCLUDES_PATTERNS = new HashSet<>();
        SESSION_EXCLUDES_PATTERNS.add("/");
        SESSION_EXCLUDES_PATTERNS.add("/druid/*");
        SESSION_EXCLUDES_PATTERNS.add(ExcludesConstants.ERROR_PATH + LabelConstants.SLASH_STAR);
        INTERCEPT_EXCLUDES_PATTERNS = new HashSet<>();
        INTERCEPT_EXCLUDES_PATTERNS.add("/");
        INTERCEPT_EXCLUDES_PATTERNS.add("/druid/*");
        INTERCEPT_EXCLUDES_PATTERNS.add(ExcludesConstants.ERROR_PATH + LabelConstants.SLASH_DOUBLE_STAR);
    }

    public static void addFilterExcludes(String... excludes) {
        SESSION_EXCLUDES_PATTERNS.addAll(Arrays.asList(excludes));
    }

    public static void addCSRFExcludes(String... excludes) {
        INTERCEPT_EXCLUDES_PATTERNS.addAll(Arrays.asList(excludes));
    }

    public static String getFilterExcludes() {
        return toString(SESSION_EXCLUDES_PATTERNS, LabelConstants.COMMA);
    }

    public static String[] getCSRFExcludes() {
        return INTERCEPT_EXCLUDES_PATTERNS.toArray(new String[]{});
    }

    /**
     * 全局规则校验
     *
     * @param requestURI
     * @return
     */
    public static boolean isExclusion(String requestURI) {
        // 静态资源责任链不为空，并且当前请求的URL已处理过
        if (null != STATIC_RESOURCE.get() && STATIC_RESOURCE.get().containsKey(requestURI)) {
            return STATIC_RESOURCE.get().get(requestURI);
        }
        // request映射责任链不为空，并且当前请求的URL已处理过
        else if (null != REQUEST_MAPPING.get() && REQUEST_MAPPING.get().containsKey(requestURI)) {
            return REQUEST_MAPPING.get().get(requestURI);
        } else {
            return verify(requestURI);
        }
    }

    /**
     * 自定义规则校验
     *
     * @param requestURI
     * @param excludes
     * @return
     */
    public static boolean isExclusion(String requestURI, Set<String> excludes) {
        // 静态资源责任链不为空，并且当前请求的URL已处理过
        if (null != STATIC_RESOURCE.get() && STATIC_RESOURCE.get().containsKey(requestURI)) {
            return STATIC_RESOURCE.get().get(requestURI);
        } else {
            return verify(requestURI, excludes);
        }
    }

    /**
     * 静态资源校验
     *
     * @param url
     * @return
     */
    public static boolean matches(String url) {
        return pattern.matcher(url).matches();
    }

    /**
     * 根据条件校验
     *
     * @param pattern
     * @param source
     * @return
     */
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
        boolean static_ = false, request_ = false;
        if (null == STATIC_RESOURCE.get()) {
            STATIC_RESOURCE.set(new HashMap<>());
        }
        if (null == REQUEST_MAPPING.get()) {
            REQUEST_MAPPING.set(new HashMap<>());
        }
        if (SESSION_EXCLUDES_PATTERNS != null && requestURI != null) {
            requestURI = formatRequestURI(requestURI);
            static_ = matches(requestURI);
            // 如果是静态资源
            if (static_) {
                STATIC_RESOURCE.get().put(url, true);
            } else {
                request_ = excludesPattern(requestURI, SESSION_EXCLUDES_PATTERNS);
                REQUEST_MAPPING.get().put(url, request_);
            }
        }
        return static_ || request_;
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
    public static final String ERROR_PATH = "/error";
    public static final String SERVICE_SOA = "soa";
    public static String CONTEXT_PATH;
    public static String SESSION_AUTHENTICATION;


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
