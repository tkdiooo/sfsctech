package com.sfsctech.common.util;

import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.constants.LabelConstants;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Class HttpUtil
 *
 * @author 张麒 2017/3/29.
 * @version Description:
 */
public class HttpUtil {

    public static String getFullUrlRequest(HttpServletRequest request) {
        return request.getScheme() + LabelConstants.COLON + LabelConstants.DOUBLE_SLASH + request.getServerName() + LabelConstants.COLON + request.getServerPort() + request.getRequestURI() + (StringUtil.isBlank(request.getQueryString()) ? "" : (LabelConstants.QUESTION + request.getQueryString()));

    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxParam = request.getParameter(CommonConstants.AJAX_TIME_FRESH);
        String ajaxAccept = request.getHeader(CommonConstants.AJAX_ACCEPT_CONTENT_TYPE[0]);
        String ajaxHeader = request.getHeader(CommonConstants.AJAX_HEADER_CONTENT_TYPE[0]);
        return StringUtils.hasText(ajaxParam) || CommonConstants.AJAX_ACCEPT_CONTENT_TYPE[1].equalsIgnoreCase(ajaxAccept) || (StringUtil.isNotBlank(ajaxHeader) && CommonConstants.AJAX_HEADER_CONTENT_TYPE[1].equalsIgnoreCase(ajaxHeader));
    }

    /**
     * 获取请求的IP地址
     *
     * @param request Request
     * @return Request IP Address
     */
    public String getRequestIP(HttpServletRequest request) {
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
}
