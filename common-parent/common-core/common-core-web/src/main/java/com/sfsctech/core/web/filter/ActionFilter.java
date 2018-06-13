package com.sfsctech.core.web.filter;

import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.web.tools.action.ActionHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class ActionFilter
 *
 * @author 张麒 2018-6-13.
 * @version Description:
 */
public class ActionFilter extends BaseFilter {

    public static final int FILTER_ORDER = 11;

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ActionHolder.setRequest((HttpServletRequest) request);
        chain.doFilter(ActionHolder.getRequest(), response);
    }
}
