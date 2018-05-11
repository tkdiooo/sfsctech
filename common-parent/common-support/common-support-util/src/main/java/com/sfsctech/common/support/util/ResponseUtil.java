package com.sfsctech.common.support.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class ResponseUtils
 *
 * @author 张麒 2016/5/16.
 * @version Description:
 */
public class ResponseUtil {

    public static void writeJson(String json, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        setNoCacheHeaders(response);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void writeText(String text, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=UTF-8");
        setNoCacheHeaders(response);
        response.getWriter().write(text);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void writeHtml(String text, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        setNoCacheHeaders(response);
        response.getWriter().write(text);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void writeJavascript(String javascript, HttpServletResponse response) throws IOException {
        response.setContentType("text/javascript; charset=UTF-8");
        setNoCacheHeaders(response);
        response.getWriter().write(javascript);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void setNoCacheHeaders(HttpServletResponse response) {
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers.
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
    }
}
