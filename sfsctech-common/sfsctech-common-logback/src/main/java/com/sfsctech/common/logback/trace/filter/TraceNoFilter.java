package com.sfsctech.common.logback.trace.filter;

import com.sfsctech.common.logback.trace.util.TraceNoUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Class TraceNoFilter
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        TraceNoUtils.newTraceNo();
        try {
            chain.doFilter(request, response);
        } finally {
            TraceNoUtils.clearTraceNo();
        }
    }

    @Override
    public void destroy() {

    }
}
