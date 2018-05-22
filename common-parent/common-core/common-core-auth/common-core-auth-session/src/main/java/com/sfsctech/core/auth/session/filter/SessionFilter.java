package com.sfsctech.core.auth.session.filter;


import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.core.auth.base.constants.SessionConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class SessionFilter
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class SessionFilter extends BaseFilter {

    private String welcomeFile;

    @Override
    public void init(FilterConfig filterConfig) {
        this.welcomeFile = filterConfig.getInitParameter("welcomeFile");
    }

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        logger.info("Request path：" + requestURI);
        if (FilterHandler.isExclusion(requestURI, super.excludesPattern)) {
            logger.info("Don't need to intercept the path[" + requestURI + "]");
            chain.doFilter(request, response);
            return;
        }
        if (null != request.getSession().getAttribute(SessionConstants.CONST_UAMS_ASSERTION)) {
            logger.info("User Session valid");
            chain.doFilter(request, response);
            return;
        }
        String index_url = this.welcomeFile;
        String form_url = HttpUtil.getFullUrl(request);
        if (StringUtil.isNotBlank(form_url)) {
            index_url += LabelConstants.QUESTION + SessionConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.DEs3CBC, form_url);
        }
        logger.info("User Session invalidation, jump page[" + index_url + "]");
        if (HttpUtil.isAjaxRequest(request)) {
            response.getWriter().write("<script>window.location.href='" + index_url + "';</script>");
        } else {
            response.sendRedirect(index_url);
        }
    }
}
