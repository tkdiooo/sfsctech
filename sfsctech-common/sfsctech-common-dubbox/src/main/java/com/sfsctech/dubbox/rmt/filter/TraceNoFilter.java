package com.sfsctech.dubbox.rmt.filter;


import com.sfsctech.dubbox.rmt.util.TraceNoUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * Class TraceNoFilter
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
public class TraceNoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
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
