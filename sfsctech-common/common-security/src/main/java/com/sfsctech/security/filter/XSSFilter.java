package com.sfsctech.security.filter;

import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.FilterConstants;
import com.sfsctech.security.session.SessionHolder;
import com.sfsctech.security.session.SessionInfo;
import com.sfsctech.security.xss.XSSHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Class XssFilter
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class XSSFilter extends BaseFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.contextPath = filterConfig.getServletContext().getContextPath();
        super.init(filterConfig.getInitParameter(FilterConstants.FILTER_EXCLUDES));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest requests = (HttpServletRequest) request;
        System.out.println(requests.getRequestURI());
        System.out.println(requests.getServletPath());
        if (!super.isExclusion(((HttpServletRequest) request).getRequestURI())) {
            return;
        }
        try {
            SessionInfo session = new SessionInfo();
            SessionHolder.setSessionInfo(session);
            chain.doFilter(new XSSHttpServletRequestWrapper((HttpServletRequest) request), response);
        } finally {
            System.out.println("clear");
            SessionHolder.clear();
        }
    }

    @Override
    public void destroy() {
        logger.debug("(XssFilter) destroy");
    }

}
