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
        logger.info("请求路径：[" + requestURI + "]，" + (bool ? "无需" : "需要") + "执行" + getClass().getSimpleName() + ".doHandler()。");
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
}
