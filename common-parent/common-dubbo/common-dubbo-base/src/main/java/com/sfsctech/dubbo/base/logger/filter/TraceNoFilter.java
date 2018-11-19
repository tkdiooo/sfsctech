package com.sfsctech.dubbo.base.logger.filter;




import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.core.logger.util.TraceNoUtil;

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

    public static final int FILTER_ORDER = 0;

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
}
