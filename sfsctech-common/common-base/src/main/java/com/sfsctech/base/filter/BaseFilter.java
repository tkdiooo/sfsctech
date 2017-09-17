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
    protected Set<String> excludesPattern;

    public void setExcludesPattern(Set<String> excludesPattern) {
        this.excludesPattern = excludesPattern;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        // 全局filter路径校验（静态资源、页面模板、Spring默认错误页面、druid分析页面）
        if (ExcludesConstants.isExclusion(requestURI)) {
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
