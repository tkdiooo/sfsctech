package com.sfsctech.cache.inf;

import java.util.Date;

/**
 * Class ICacheService
 *
 * @author 张麒 2016/5/13.
 * @version Description:
 */
public interface ICacheService<K, V> {
    /**
     * <pre>
     * 保存数据到CACHE中
     * </pre>
     *
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     * <pre>
     * 保存数据到CACHE中锁的
     * </pre>
     *
     * @param key
     * @param value
     * @return
     */
    V add(K key, V value);

    /**
     * <pre>
     * 保存数据到CACHE中，有效期为秒
     * </pre>
     *
     * @param key
     * @param value
     * @param timeOut
     * @return
     */
    V putTimeOut(K key, V value, int timeOut);

    /**
     * <pre>
     * 保存数据到CACHE中，有效期为秒锁的
     * </pre>
     *
     * @param key
     * @param value
     * @param TTL
     * @return
     */
    V add(K key, V value, Integer TTL);

    /**
     * 保存数据到CACHE中，有效期为秒锁的
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    Object addTimeOut(String key, Object value, int timeout);

    /**
     * <pre>
     * 从CACHE中获取数据
     * </pre>
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * <pre>
     * 将数据从CACHE移走
     * </pre>
     *
     * @param key
     * @return
     */
    V remove(K key);

    /**
     * <pre>
     * 将CACHE中的数据替换掉
     * </pre>
     *
     * @param key
     * @param value
     * @return
     */
    V replace(K key, V value);

    /**
     * <pre>
     * 清空CACHE
     * </pre>
     *
     * @return
     */
    boolean flushAll();
}
