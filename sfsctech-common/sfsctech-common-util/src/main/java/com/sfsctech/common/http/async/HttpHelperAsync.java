package com.sfsctech.common.http.async;

import com.sfsctech.common.http.ResponseContent;
import com.sfsctech.common.http.synch.HttpHelper;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Class HttpHelperAsync
 *
 * @author 张麒 2016/4/12.
 * @version Description:
 */
public class HttpHelperAsync {

    private HttpHelperAsync() {

    }

    private static void doExecute(Runnable runnable) {
        Thread reactorThread = new Thread(runnable);
        reactorThread.start();
    }

    public static Future<ResponseContent> getUrlRespContent(final String url, final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.getUrlRespContent(url);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    public static Future<ResponseContent> getUrlRespContent(final String url, final String urlEncoding, final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.getUrlRespContent(url, urlEncoding);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    public static Future<ResponseContent> postUrl(final String url, final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.postUrl(url);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    public static Future<ResponseContent> uploadFiles(final String url, final Map<String, Object> paramsMap,
                                                      final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.uploadFiles(url, paramsMap);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    public static Future<ResponseContent> postJsonEntity(final String url, final String jsonBody, final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.postJsonEntity(url, jsonBody);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    public static Future<ResponseContent> postXmlEntity(final String url, final String xmlBody, final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.postXmlEntity(url, xmlBody);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    public static Future<ResponseContent> postEntity(final String url, final Map<String, String> paramsMap, final FutureCallback<ResponseContent> futureCallback) {
        final AsyncHttpFuture<ResponseContent> future = buildHttpFuture(futureCallback);
        doExecute(() -> {
            try {
                ResponseContent content = HttpHelper.postEntity(url, paramsMap);
                future.completed(content);
            } catch (Exception e) {
                future.failed(e);
            }
        });
        return future;
    }

    private static AsyncHttpFuture<ResponseContent> buildHttpFuture(final FutureCallback<ResponseContent> futureCallback) {
        AsyncHttpFuture<ResponseContent> future = new AsyncHttpFuture<>(futureCallback);
        return future;
    }
}
