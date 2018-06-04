package com.sfsctech.core.security.domain;

/**
 * Class Whitelist
 *
 * @author 张麒 2018-6-4.
 * @version Description:
 */
public class Whitelist {

    private String ip;
    private int timeSpan;
    private int limit;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(int timeSpan) {
        this.timeSpan = timeSpan;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
