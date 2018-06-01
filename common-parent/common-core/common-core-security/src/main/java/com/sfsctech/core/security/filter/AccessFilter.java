package com.sfsctech.core.security.filter;

import com.sfsctech.core.security.domain.Access;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class NetSecurityInterceptor
 *
 * @author 张麒 2018-5-25.
 * @version Description:
 */
public class AccessFilter implements Filter {

    public static final int FILTER_ORDER = 1;

    private final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    private Map<String, Access> crossDomain;

    public AccessFilter(Map<String, Access> crossDomain) {
        if (null != crossDomain) {
            this.crossDomain = crossDomain;
        } else {
            this.crossDomain = new HashMap<>();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String method = request.getMethod();
        final String domain = HttpUtil.getDomain(request);
        final String request_url = HttpUtil.getFullUrl(request);
        final String ret_url = request.getHeader("Referer");
        // 请求方法验证
        if (!"GET".equals(method) && !"POST".equals(method) && !"HEAD".equals(method) && !DispatcherType.ERROR.equals(request.getDispatcherType())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Request only permit GET POST or HEAD");
            return;
        }
        // 跨域访问验证
        if (this.crossDomain.containsKey(domain)) {
            Access access = crossDomain.get(domain);
            // 跨域请求方法错误
            if (StringUtil.isNotBlank(access.getAccessControlAllowMethods()) && !access.getAccessControlAllowMethods().equals(method)) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Request only permit " + access.getAccessControlAllowMethods());
                return;
            }
            if (StringUtil.isNotBlank(access.getAccessControlAllowHeaders())) {
                // 请求Contetn-Type支持 application/json格式
                response.setHeader("Access-Control-Allow-Headers", access.getAccessControlAllowHeaders());
                return;
            }
            if (null != access.getAccessControlAllowCredentials()) {
                // 跨域Cookies设置
                response.setHeader("Access-Control-Allow-Credentials", access.getAccessControlAllowCredentials().toString());
                return;
            }
            response.setHeader("Access-Control-Allow-Origin", domain);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
