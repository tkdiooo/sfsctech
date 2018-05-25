package com.sfsctech.core.security.filter;

import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class NetSecurityInterceptor
 *
 * @author 张麒 2018-5-25.
 * @version Description:
 */
public class RequestFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String method = request.getMethod();
        if (!"GET".equals(method) && !"POST".equals(method) && !"HEAD".equals(method) && !DispatcherType.ERROR.equals(request.getDispatcherType())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Request only permit GET POST or HEAD");
            return;
        }
        String ret_url = request.getHeader("Referer");
        if (StringUtil.isNotBlank(ret_url) && !ret_url.startsWith(HttpUtil.getDomain(request))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden illegal request");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
