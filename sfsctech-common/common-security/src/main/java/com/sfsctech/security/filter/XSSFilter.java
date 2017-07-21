package com.sfsctech.security.filter;

import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.security.session.SessionHolder;
import com.sfsctech.security.session.SessionInfo;
import com.sfsctech.security.xss.XSSHttpServletRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class XssFilter
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class XSSFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            SessionInfo session = new SessionInfo();
            SessionHolder.setSessionInfo(session);
            chain.doFilter(new XSSHttpServletRequestWrapper((HttpServletRequest) request), response);
        } finally {
            SessionHolder.clear();
        }
    }

}
