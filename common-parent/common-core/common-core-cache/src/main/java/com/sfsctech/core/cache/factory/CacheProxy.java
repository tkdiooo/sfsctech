package com.sfsctech.core.cache.factory;

/**
 * Class CacheProxy
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
public interface CacheProxy<K, V> {

    /**
     * <pre>
     * 保存数据到CACHE中
     * </pre>
     *
     * @param key   K
     * @param value V
     * @return V
     */
    V put(K key, V value);

    /**
     * <pre>
     * 保存数据到CACHE中锁的
     * </pre>
     *
     * @param key   K
     * @param value V
     * @return V
     */
    V add(K key, V value);

    /**
     * <pre>
     * 保存数据到CACHE中，有效期为秒
     * </pre>
     *
     * @param key     K
     * @param value   V
     * @param timeOut time
     * @return V
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
     * @param key     K
     * @param value   V
     * @param timeout time
     * @return V
     */
    V addTimeOut(String key, Object value, int timeout);

    /**
     * <pre>
     * 从CACHE中获取数据
     * </pre>
     *
     * @param key K
     * @return V
     */
    V get(K key);

    /**
     * <pre>
     * 将数据从CACHE移走
     * </pre>
     *
     * @param key K
     * @return V
     */
    V remove(K key);

    /**
     * <pre>
     * 将CACHE中的数据替换掉
     * </pre>
     *
     * @param key   K
     * @param value V
     * @return V
     */
    V replace(K key, V value);

    /**
     * <pre>
     * 清空CACHE
     * </pre>
     *
     * @return boolean
     */
    boolean flushAll();
}
