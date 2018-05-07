//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.ex;

import org.springframework.http.HttpEntity;

public class HttpExecuteErrorException extends RuntimeException {
    private static final long serialVersionUID = -524379777219983097L;

    public HttpExecuteErrorException(HttpEntity httprequest) {
        super("执行http请求时发生异常, http请求: " + httprequest.toString());
    }
}
