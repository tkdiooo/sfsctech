package com.sfsctech.support.common.util;


import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.WebsiteConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class HttpUtil
 *
 * @author 张麒 2017/3/29.
 * @version Description:
 */
public class HttpUtil {

    public static String getRootPattern() {
        return StringUtil.isNotBlank(WebsiteConstants.CONTEXT_PATH) ? WebsiteConstants.CONTEXT_PATH + LabelConstants.SLASH_DOUBLE_STAR : LabelConstants.SLASH_DOUBLE_STAR;
    }

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

    // 从类unix机器上获取mac地址
    public static String getMac(String ip) {
        String mac = "";
        if (ip != null) {
            try {
                Process process = Runtime.getRuntime().exec("arp " + ip);
                InputStreamReader ir = new InputStreamReader(process.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String line;
                StringBuilder s = new StringBuilder();
                while ((line = input.readLine()) != null) {
                    s.append(line);
                }
                mac = s.toString();
                if (StringUtils.isNotBlank(mac)) {
                    mac = mac.substring(mac.indexOf(":") - 2, mac.lastIndexOf(":") + 3);
                }
                return mac;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mac;
    }

    public static String getMacAddrByIp(String ip) {
        String macAddr = null;
        try {
            Process process = Runtime.getRuntime().exec("nbtstat -a " + ip);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            Pattern pattern = Pattern.compile("([A-F0-9]{2}-){5}[A-F0-9]{2}");
            Matcher matcher;
            for (String strLine = br.readLine(); strLine != null;
                 strLine = br.readLine()) {
                matcher = pattern.matcher(strLine);
                if (matcher.find()) {
                    macAddr = matcher.group();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macAddr;
    }

    // 从windows机器上获取mac地址
    public static String getMacInWindows(final String ip) {
        String result = "";
        String[] cmd = {"cmd", "/c", "ping " + ip};
        String[] another = {"cmd", "/c", "ipconfig -all"};
        // 获取执行命令后的result
        String cmdResult = callCmd(cmd, another);
        // 从上一步的结果中获取mac地址
        result = filterMacAddress(ip, cmdResult, "-");
        return result;
    }

    // 命令执行
    private static String callCmd(String[] cmd, String[] another) {
        StringBuilder result = new StringBuilder();
        String line;
        try {
            Runtime rt = Runtime.getRuntime();
            // 执行第一个命令
            Process proc = rt.exec(cmd);
            proc.waitFor();
            // 执行第二个命令
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    // 获取mac地址
    private static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            result = matcher.group(1);
            // 因计算机多网卡问题，截取紧靠IP后的第一个mac地址
            int num = sourceString.indexOf(ip) - sourceString.indexOf(": " + result + " ");
            if (num > 0 && num < 300) {
                break;
            }
        }
        return result;
    }
}
