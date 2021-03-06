package com.sfsctech.core.security.filter;

import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.security.xss.XSSHttpServletRequestWrapper;

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

    public static final int FILTER_ORDER = 10;

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XSSHttpServletRequestWrapper((HttpServletRequest) request), response);
    }

}
