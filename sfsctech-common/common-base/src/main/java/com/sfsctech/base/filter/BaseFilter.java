package com.sfsctech.base.filter;

import com.sfsctech.constants.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class BaseFilter
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public abstract class BaseFilter implements Filter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private Set<String> excludesPattern;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String params = filterConfig.getInitParameter(SecurityConstants.FILTER_EXCLUDES_KEY);
        if (StringUtils.isNotBlank(params)) {
            logger.info(getClass() + "[" + SecurityConstants.FILTER_EXCLUDES_KEY + "：" + params + "]");
            this.excludesPattern = Arrays.stream(params.split("\\s*,\\s*")).collect(Collectors.toSet());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        boolean bool = (null != this.excludesPattern && this.excludesPattern.size() > 0) ? SecurityConstants.isExclusion(requestURI, this.excludesPattern) : SecurityConstants.isExclusion(requestURI);
        logger.info(getClass() + " isExclusion：[" + bool + "] requestURI：[" + requestURI + "]");
        if (bool) {
            chain.doFilter(request, response);
        } else {
            doHandler(request, response, chain);
        }
    }

    @Override
    public void destroy() {
        logger.debug(getClass() + " destroy");
    }

    public abstract void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

//    protected boolean isExclusion(String requestURI) {
//        // 责任链不为空，并且当前请求的URL已处理过
//        if (null != SecurityConstants.handler.get() && SecurityConstants.handler.get().containsKey(requestURI)) {
//            return SecurityConstants.handler.get().get(requestURI);
//        } else if (null != this.excludesPattern && this.excludesPattern.size() > 0) {
//            return SecurityConstants.verify(requestURI, this.excludesPattern);
//        } else {
//            return SecurityConstants.verify(requestURI);
//        }
//    }

//    protected boolean matches(String url) {
//        return pattern.matcher(url).matches();
//    }
//
//    protected boolean matches(String pattern, String source) {
//        if (pattern != null && source != null) {
//            pattern = pattern.trim();
//            source = source.trim();
//            int start;
//            if (pattern.endsWith("*")) {
//                start = pattern.length() - 1;
//                if (source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
//                    return true;
//                }
//            } else if (pattern.startsWith("*")) {
//                start = pattern.length() - 1;
//                if (source.length() >= start && source.endsWith(pattern.substring(1))) {
//                    return true;
//                }
//            } else if (pattern.contains("*")) {
//                start = pattern.indexOf("*");
//                int end = pattern.lastIndexOf("*");
//                if (source.startsWith(pattern.substring(0, start)) && source.endsWith(pattern.substring(end + 1))) {
//                    return true;
//                }
//            } else if (pattern.equals(source)) {
//                return true;
//            }
//            return false;
//        } else {
//            return false;
//        }
//    }

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
//
//    private boolean excludesPattern(String requestURI) {
//        Iterator<String> var2 = this.excludesPattern.iterator();
//        do {
//            if (!var2.hasNext()) return false;
//        } while (!this.matches(var2.next(), requestURI));
//        return true;
//    }
}
