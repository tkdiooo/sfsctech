package com.sfsctech.common.support.util;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class SystemUtil
 *
 * @author 张麒 2016/4/7.
 * @version Description:
 */
public class SystemUtil extends SystemUtils {

    private static final Logger logger = LoggerFactory.getLogger(SystemUtil.class);

    /**
     * 获取机器名
     *
     * @return Host Name
     */
    public static String getHostName() {
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
        return hostname;
    }

    /**
     * 获取机器的IP地址
     *
     * @return Host Address
     */
    public static String getHostIP() {
        String hostaddress = "";
        try {
            hostaddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
        return hostaddress;
    }

}
