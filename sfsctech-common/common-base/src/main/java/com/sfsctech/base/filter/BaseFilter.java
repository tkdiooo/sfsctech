package com.sfsctech.base.filter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class BaseFilter
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public abstract class BaseFilter {

    private Set<String> excludesPattern;
    protected String contextPath;
    private static Pattern pattern = Pattern.compile("^.*?\\.(js|bmp|css|jpg|gif|png|eot|svg|ttf|woff|ico|woff2)$");

    protected void init(String param) {
        if (param != null && param.trim().length() != 0) {
            this.excludesPattern = Arrays.stream(param.split("\\s*,\\s*")).collect(Collectors.toSet());
        }
    }

    protected boolean isExclusion(String requestURI) {
        if (this.excludesPattern != null && requestURI != null) {
            if (this.contextPath != null && requestURI.startsWith(this.contextPath)) {
                requestURI = requestURI.substring(this.contextPath.length());
                if (!requestURI.startsWith("/")) {
                    requestURI = "/" + requestURI;
                }
            }
            return this.matches(requestURI) || excludesPattern(requestURI);
        } else {
            return false;
        }
    }

    private boolean excludesPattern(String requestURI) {
        Iterator<String> var2 = this.excludesPattern.iterator();

        do {
            if (!var2.hasNext()) return false;
        } while (!this.matches(var2.next(), requestURI));

        return true;
    }

    protected boolean matches(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    protected boolean matches(String pattern, String source) {
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
}
