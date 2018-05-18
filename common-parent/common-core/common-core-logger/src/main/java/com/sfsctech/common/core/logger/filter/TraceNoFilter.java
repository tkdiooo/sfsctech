package com.sfsctech.common.core.logger.filter;


import com.sfsctech.common.core.base.filter.BaseFilter;
import com.sfsctech.common.core.logger.util.TraceNoUtil;
import com.sfsctech.common.support.util.HttpUtil;

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
        try {
            logger.info("requestURI[" + HttpUtil.getFullUrl(((HttpServletRequest) request)) + "]");
            chain.doFilter(request, response);
        } finally {
            TraceNoUtil.clearTraceNo();
        }
    }

    @Override
    public void destroy() {
        System.out.println("sdsdsd");
    }
}
