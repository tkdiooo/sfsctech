package com.sfsctech.core.security.xss;

import com.sfsctech.support.common.util.HtmlEscapeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Class XssHttpServletRequestWrapper
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

//    private final byte[] body;

    public XSSHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // body = StreamUtil.readBytes(request.getReader(), JoddDefault.encoding);
        // 因为http协议默认传输的编码就是iso-8859-1,如果使用utf-8转码乱码的话，可以尝试使用iso-8859-1
//        body = StreamUtil.readBytes(request.getReader(), "utf-8");
    }

//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(getInputStream()));
//    }
//
//    @Override
//    public ServletInputStream getInputStream() {
//        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
//        return new ServletInputStream() {
//
//            @Override
//            public boolean isFinished() {
//                return false;
//            }
//
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {
//
//            }
//
//            @Override
//            public int read() {
//                return bais.read();
//            }
//        };
//    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = HtmlEscapeUtil.xssEncode(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return HtmlEscapeUtil.xssEncode(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return HtmlEscapeUtil.xssEncode(value);
    }

}
