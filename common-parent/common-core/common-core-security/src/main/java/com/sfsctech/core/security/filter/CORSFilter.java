package com.sfsctech.core.security.filter;

import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.security.domain.Access;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class NetSecurityInterceptor
 *
 * @author 张麒 2018-5-25.
 * @version Description:
 */
public class CORSFilter extends BaseFilter {

    public static final int FILTER_ORDER = 2;

    private final Logger logger = LoggerFactory.getLogger(CORSFilter.class);

    private Map<String, Access> crossDomain;

    public CORSFilter(Map<String, Access> crossDomain) {
        if (null != crossDomain) {
            this.crossDomain = crossDomain;
        } else {
            this.crossDomain = new HashMap<>();
        }
    }

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String domain = request.getHeader("Origin");
        // 跨域访问验证
        if (this.crossDomain.containsKey(domain)) {
            logger.info("cross-domain[Origin]：" + domain);
            Access access = crossDomain.get(domain);
            final String method = request.getMethod();
            // 跨域请求方法错误
            if (StringUtil.isNotBlank(access.getAccessControlAllowMethods()) && !access.getAccessControlAllowMethods().equals(method)) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Request only permit " + access.getAccessControlAllowMethods());
                return;
            }
            if (StringUtil.isNotBlank(access.getAccessControlAllowHeaders())) {
                // 请求Contetn-Type支持 application/json格式
                response.setHeader("Access-Control-Allow-Headers", access.getAccessControlAllowHeaders());
            }
            if (null != access.getAccessControlAllowCredentials()) {
                // 跨域Cookies设置
                response.setHeader("Access-Control-Allow-Credentials", access.getAccessControlAllowCredentials().toString());
            }
            response.setHeader("Access-Control-Allow-Origin", domain);
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
