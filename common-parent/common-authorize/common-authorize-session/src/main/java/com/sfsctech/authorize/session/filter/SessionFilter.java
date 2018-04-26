package com.sfsctech.authorize.session.filter;

import com.sfsctech.constants.SSOConstants;
import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.ExcludesConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.spring.properties.AppConfig;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        logger.info("Request path：" + requestURI);
        if (ExcludesConstants.isExclusion(requestURI, excludesPattern)) {
            logger.info("Don't need to intercept the path[" + requestURI + "]");
            chain.doFilter(request, response);
            return;
        }
        if (null != request.getSession().getAttribute(SSOConstants.CONST_UAMS_ASSERTION)) {
            logger.info("User Session valid");
            chain.doFilter(request, response);
            return;
        }
        AppConfig appConfig = SpringContextUtil.getBean(AppConfig.class);
        String index_url = request.getContextPath() + appConfig.getWebsiteProperties().getSupport().getWelcomeFile();
        String form_url = HttpUtil.getFullUrl(request);
        if (StringUtil.isNotBlank(form_url)) {
            index_url += LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.DEs3CBC, form_url);
        }
        logger.info("User Session invalidation, jump page[" + index_url + "]");
        if (HttpUtil.isAjaxRequest(request)) {
            response.getWriter().write("<script>window.location.href='" + index_url + "';</script>");
        } else {
            response.sendRedirect(index_url);
        }
    }
}
