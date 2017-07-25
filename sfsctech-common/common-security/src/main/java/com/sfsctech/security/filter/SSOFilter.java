package com.sfsctech.security.filter;

import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.security.session.SessionHolder;
import com.sfsctech.security.session.SessionInfo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOFilter extends BaseFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionHolder.setSessionInfo(new SessionInfo());
        try {
            super.doFilter(request, response, chain);
        } finally {
            SessionHolder.clear();
        }
    }

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
