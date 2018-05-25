package com.sfsctech.core.security.filter;


import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.support.common.util.HttpUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class SecurityFilter
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
public class DDOCFilter implements Filter {

    private SecurityProperties.Ddos ddos;

    public DDOCFilter(SecurityProperties.Ddos ddos) {
        this.ddos = ddos;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        StringBuffer url = request.getRequestURL();
        System.out.println(url);
        String ip = HttpUtil.getRequestIP(request);
        String domain = HttpUtil.getDomain(request);
        if (null != ddos.getAccessControlAllowOrigin() && ddos.getAccessControlAllowOrigin().contains(domain)) {
            // 跨域请求白名单
            response.setHeader("Access-Control-Allow-Origin", domain);
            // 请求Contetn-Type支持 application/json格式
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            // 跨域Cookies设置
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
