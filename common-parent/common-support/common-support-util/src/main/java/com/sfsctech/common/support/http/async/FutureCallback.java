package com.sfsctech.common.support.http.async;

/**
 * Class FutureCallback
 *
 * @author 张麒 2016/4/12.
 * @version Description:
 */
public interface FutureCallback<T> {


    /**
     * for request completed callback
     *
     * @param t T
     */
    void completed(T t);


    /**
     * for request failed callback
     *
     * @param e Exception
     */
    void failed(Exception e);


}
