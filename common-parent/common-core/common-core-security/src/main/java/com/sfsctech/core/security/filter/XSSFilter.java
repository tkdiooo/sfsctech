package com.sfsctech.core.security.filter;

import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.security.xss.XSSHttpServletRequestWrapper;
import com.sfsctech.support.common.util.HtmlEscapeUtil;
import com.sfsctech.support.common.util.ResponseUtil;
import com.sfsctech.support.common.util.StringUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

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
        XSSHttpServletRequestWrapper xssRequest = new XSSHttpServletRequestWrapper((HttpServletRequest) request);
//        if ("POST".equalsIgnoreCase(xssRequest.getMethod())) {
//            String param = this.getBodyString(xssRequest.getReader());
//            if (StringUtil.isNotBlank(param)) {
//                if (HtmlEscapeUtil.checkXSSAndSql(param)) {
//                    ResponseUtil.writeJson("访问的页面请求中有违反安全规则元素存在，拒绝访问!", (HttpServletResponse) response);
//                    return;
//                }
//            }
//        }
//        if (this.checkParameter(xssRequest)) {
//            ResponseUtil.writeJson("访问的页面请求中有违反安全规则元素存在，拒绝访问!", (HttpServletResponse) response);
//            return;
//        }
        chain.doFilter(xssRequest, response);
    }

    // 获取request请求body中参数
    private String getBodyString(BufferedReader br) {
        String inputLine;
        StringBuilder str = new StringBuilder();
        try {
            while ((inputLine = br.readLine()) != null) {
                str.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str.toString();
    }

    private boolean checkParameter(XSSHttpServletRequestWrapper xssRequest) {
        Map<String, String[]> parameterMap = xssRequest.getParameterMap();
        for (String key : parameterMap.keySet()) {
            if (null != parameterMap.get(key)) {
                for (String value : parameterMap.get(key)) {
                    if (HtmlEscapeUtil.checkXSSAndSql(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
