package com.sfsctech.support.common.util;


import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.constants.LabelConstants;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class HttpUtil
 *
 * @author 张麒 2017/3/29.
 * @version Description:
 */
public class HttpUtil {

    public static String buildUrl(String ipAddress, int port) {
        return ipAddress + ":" + port;
    }

    public static String getFullUrl(HttpServletRequest request) {
        return request.getScheme() + LabelConstants.COLON + LabelConstants.DOUBLE_SLASH + request.getServerName() + LabelConstants.COLON + request.getServerPort() + request.getRequestURI() + (StringUtil.isBlank(request.getQueryString()) ? "" : (LabelConstants.QUESTION + request.getQueryString()));
    }

    public static String getDomain(HttpServletRequest request) {
        return request.getScheme() + LabelConstants.COLON + LabelConstants.DOUBLE_SLASH + request.getServerName();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxParam = request.getParameter(CommonConstants.AJAX_TIME_FRESH);
        String ajaxAccept = request.getHeader(CommonConstants.AJAX_ACCEPT_CONTENT_TYPE[0]);
        String ajaxHeader = request.getHeader(CommonConstants.AJAX_HEADER_CONTENT_TYPE[0]);
        return StringUtil.hasText(ajaxParam) || CommonConstants.AJAX_ACCEPT_CONTENT_TYPE[1].equalsIgnoreCase(ajaxAccept) || (StringUtil.isNotBlank(ajaxHeader) && CommonConstants.AJAX_HEADER_CONTENT_TYPE[1].equalsIgnoreCase(ajaxHeader));
    }

    /**
     * 获取服务器IP地址
     */
    public static String getServerIp() {
        try {
            return convert2IPAdress(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取请求的IP地址
     *
     * @param request Request
     * @return Request IP Address
     */
    public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) return ip.substring(0, index);
            else return ip;
        }
        ip = request.getHeader("X-Real-IP");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) return ip;
        ip = request.getHeader("Proxy-Client-IP");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) return ip;
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) return ip;
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) return ip;
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) return ip;
        else return request.getRemoteAddr();
    }

    public static String convert2IPAdress(InetAddress inetAddress) {
        StringBuilder stringBuffer = new StringBuilder();
        byte[] var2 = inetAddress.getAddress();

        for (byte b : var2) {
            int value = b & 255;
            value = (value + 256) % 256;
            stringBuffer.append(value).append(".");
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }
}
