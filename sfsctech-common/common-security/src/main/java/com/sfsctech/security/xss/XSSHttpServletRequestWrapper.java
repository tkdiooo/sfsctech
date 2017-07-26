package com.sfsctech.security.xss;

import com.sfsctech.common.util.HtmlEscapeUtil;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Class XssHttpServletRequestWrapper
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XSSHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        return HtmlEscapeUtil.htmlEncode(value);
    }

}
