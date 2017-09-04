package com.sfsctech.auth.filter;

import com.sfsctech.base.filter.BaseFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Class SessionFilter
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class SessionFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
