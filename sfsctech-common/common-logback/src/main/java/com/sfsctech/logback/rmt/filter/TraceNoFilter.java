package com.sfsctech.logback.rmt.filter;

import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.FilterConstants;
import com.sfsctech.logback.rmt.util.TraceNoUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class TraceNoFilter
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoFilter extends BaseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.contextPath = filterConfig.getServletContext().getContextPath();
        super.init(filterConfig.getInitParameter(FilterConstants.FILTER_EXCLUDES));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest requests = (HttpServletRequest) request;
        System.out.println(requests.getRequestURI());
        System.out.println(requests.getServletPath());
        if (super.isExclusion(((HttpServletRequest) request).getRequestURI())) {
            return;
        }
        TraceNoUtil.newTraceNo();
        try {
            chain.doFilter(request, response);
        } finally {
            TraceNoUtil.clearTraceNo();
        }
    }

    @Override
    public void destroy() {

    }
}
