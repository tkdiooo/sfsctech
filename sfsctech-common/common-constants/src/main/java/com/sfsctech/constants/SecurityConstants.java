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

    public static final Pattern pattern = Pattern.compile("^.*?\\.(js|bmp|css|jpg|gif|png|eot|svg|ttf|woff|ico|woff2)$");

//    private boolean verify(String requestURI) {
//        final String url = requestURI;
//        if (null == handler.get()) {
//            handler.set(new HashMap<>());
//        }
//        handler.get().put(url, false);
//        if (this.excludesPattern != null && requestURI != null) {
//            if (this.contextPath != null && requestURI.startsWith(this.contextPath)) {
//                requestURI = requestURI.substring(this.contextPath.length());
//                if (!requestURI.startsWith("/")) {
//                    requestURI = "/" + requestURI;
//                }
//            }
//            handler.get().put(url, this.matches(requestURI) || excludesPattern(requestURI));
//        }
//        return handler.get().get(url);
//    }

//    private boolean excludesPattern(String requestURI) {
//        Iterator<String> var2 = this.excludesPattern.iterator();
//        do {
//            if (!var2.hasNext()) return false;
//        } while (!this.matches(var2.next(), requestURI));
//        return true;
//    }

    // Filter Attribute
    //-------------------------------------------------------------------------------------------
    public static final String FILTER_EXCLUDES_KEY = "exclusions";

    public static Set<String> FILTER_EXCLUDES_VALUE;

    static {
        FILTER_EXCLUDES_VALUE = new HashSet<>();
        FILTER_EXCLUDES_VALUE.add("/druid/*");
    }

    public static void addFilterExcludes(String... excludes) {
        FILTER_EXCLUDES_VALUE.addAll(Arrays.asList(excludes));
    }

    public static String getFilterExcludes() {
        return toString(FILTER_EXCLUDES_VALUE, LabelConstants.COMMA);
    }

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
