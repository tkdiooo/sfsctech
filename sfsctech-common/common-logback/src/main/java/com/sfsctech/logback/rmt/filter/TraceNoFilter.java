package com.sfsctech.logback.rmt.filter;

import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.logback.rmt.util.TraceNoUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class TraceNoFilter
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        TraceNoUtil.newTraceNo();
        logger.info("traceNo：[" + TraceNoUtil.getTraceNo() + "] requestURL:[" + ((HttpServletRequest) request).getRequestURI() + "]");
        try {
            chain.doFilter(request, response);
        } finally {
            TraceNoUtil.clearTraceNo();
        }
    }
}
