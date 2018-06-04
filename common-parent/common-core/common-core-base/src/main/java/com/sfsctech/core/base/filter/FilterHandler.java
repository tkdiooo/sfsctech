package com.sfsctech.core.base.filter;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.WebsiteConstants;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Class FilterHandler
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
public class FilterHandler {

    private static final Pattern pattern = Pattern.compile("^.*?\\.(js|bmp|css|jpg|gif|png|eot|svg|ttf|woff|ico|woff2|map)$");
    private static final ThreadLocal<Map<String, Boolean>> STATIC_RESOURCE = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Boolean>> REQUEST_MAPPING = new ThreadLocal<>();
    public static final String ERROR_PATH = "/error";

    // 过滤器基础过滤条件，默认不过滤/、/druid/*、/error/*
    public static Set<String> FILTER_EXCLUDES_PATTERNS;
    // 拦截器基础过滤条件，默认不过滤/、/druid/*、/error/**
    public static Set<String> INTERCEPT_EXCLUDES_PATTERNS;

    static {
        FILTER_EXCLUDES_PATTERNS = new HashSet<>();
        FILTER_EXCLUDES_PATTERNS.add("/");
        FILTER_EXCLUDES_PATTERNS.add("/druid/*");
        FILTER_EXCLUDES_PATTERNS.add(ERROR_PATH + LabelConstants.SLASH_STAR);
        INTERCEPT_EXCLUDES_PATTERNS = new HashSet<>();
        INTERCEPT_EXCLUDES_PATTERNS.add("/");
        INTERCEPT_EXCLUDES_PATTERNS.add("/druid/*");
        INTERCEPT_EXCLUDES_PATTERNS.add(ERROR_PATH + LabelConstants.SLASH_DOUBLE_STAR);
    }

    public static void addFilterExcludes(String... excludes) {
        FILTER_EXCLUDES_PATTERNS.addAll(Arrays.asList(excludes));
    }

    public static void addCSRFExcludes(String... excludes) {
        INTERCEPT_EXCLUDES_PATTERNS.addAll(Arrays.asList(excludes));
    }

    public static String getFilterExcludes() {
        return toString(FILTER_EXCLUDES_PATTERNS);
    }

    public static String[] getCSRFExcludes() {
        return INTERCEPT_EXCLUDES_PATTERNS.toArray(new String[]{});
    }

    /**
     * 全局规则校验
     *
     * @param requestURI URL
     * @return Boolean
     */
    public static boolean isExclusion(final String requestURI) {
        String url = formatJsessionID(requestURI);
        // 静态资源责任链不为空，并且当前请求的URL已处理过
        if (null != STATIC_RESOURCE.get() && STATIC_RESOURCE.get().containsKey(url)) {
            return STATIC_RESOURCE.get().get(url);
        }
        // request映射责任链不为空，并且当前请求的URL已处理过
        else if (null != REQUEST_MAPPING.get() && REQUEST_MAPPING.get().containsKey(url)) {
            return REQUEST_MAPPING.get().get(url);
        } else {
            return verify(url);
        }
    }

    /**
     * 自定义规则校验
     *
     * @param requestURI URL
     * @param excludes   Set
     * @return Boolean
     */
    public static boolean isExclusion(final String requestURI, Set<String> excludes) {
        String url = formatJsessionID(requestURI);
        // 静态资源责任链不为空，并且当前请求的URL已处理过
        if (null != STATIC_RESOURCE.get() && STATIC_RESOURCE.get().containsKey(url)) {
            return STATIC_RESOURCE.get().get(url);
        } else {
            return verify(url, excludes);
        }
    }

    /**
     * 静态资源校验
     *
     * @param url URL
     * @return Boolean
     */
    public static boolean matches(String url) {
        return pattern.matcher(url).matches();
    }

    /**
     * 根据条件校验
     */
    private static boolean matches(String pattern, String source) {
        if (pattern != null && source != null) {
            pattern = pattern.trim();
            source = source.trim();
            int start;
            if (pattern.endsWith("**")) {
                start = pattern.length() - 2;
                if (source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
                    return true;
                }
            } else if (pattern.endsWith("*")) {
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
        if (FILTER_EXCLUDES_PATTERNS != null && requestURI != null) {
            requestURI = formatRequestURI(requestURI);
            static_ = matches(requestURI);
            // 如果是静态资源
            if (static_) {
                STATIC_RESOURCE.get().put(url, true);
            } else {
                request_ = excludesPattern(requestURI, FILTER_EXCLUDES_PATTERNS);
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
        if (WebsiteConstants.CONTEXT_PATH != null && requestURI.startsWith(WebsiteConstants.CONTEXT_PATH)) {
            if (requestURI.endsWith(LabelConstants.FORWARD_SLASH) && requestURI.length() > WebsiteConstants.CONTEXT_PATH.length() + 1) {
                requestURI = requestURI.substring(WebsiteConstants.CONTEXT_PATH.length(), requestURI.length() - 1);
            } else {
                requestURI = requestURI.substring(WebsiteConstants.CONTEXT_PATH.length());
            }
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }
        return requestURI;
    }

    private static String formatJsessionID(String requestURI) {
        if (requestURI.contains(";jsessionid")) {
            return requestURI.substring(0, requestURI.indexOf(";jsessionid"));
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

    private static String toString(Set<String> set) {
        StringBuilder buffer = new StringBuilder();
        set.forEach(s -> {
            if (buffer.length() > 0) {
                buffer.append(LabelConstants.COMMA);
                buffer.append(s);
            } else {
                buffer.append(s);
            }
        });
        return buffer.toString();
    }
}
