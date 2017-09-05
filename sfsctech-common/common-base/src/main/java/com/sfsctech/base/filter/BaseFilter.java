package com.sfsctech.base.filter;

import com.sfsctech.constants.ExcludesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

/**
 * Class BaseFilter
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public abstract class BaseFilter implements Filter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private Set<String> excludesPattern;

    public void setExcludesPattern(Set<String> excludesPattern) {
        this.excludesPattern = excludesPattern;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        boolean bool = (null != this.excludesPattern && this.excludesPattern.size() > 0) ? ExcludesConstants.isExclusion(requestURI, this.excludesPattern) : ExcludesConstants.isExclusion(requestURI);
        logger.info("请求路径：[" + requestURI + "]，" + (bool ? "无需" : "需要") + "执行" + getClass().getSimpleName() + ".doHandler。");
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
