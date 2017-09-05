package com.sfsctech.auth.filter;

import com.sfsctech.auth.constants.AuthConstants;
import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.constants.ExcludesConstants;

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
        boolean bool = ExcludesConstants.isExclusion(requestURI);
        logger.info("请求路径：[" + requestURI + "]，" + (bool ? "无需" : "需要") + "进行Session验证。");
        if (bool) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute(AuthConstants.CONST_UAMS_ASSERTION) == null) {
            logger.info("用户未登录");
        }
    }
}
