package com.sfsctech.cache.inf;

/**
 * Class ICacheClient
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public interface ICacheClient<T extends ICacheService<?, ?>> {

    /**
     * <pre>
     * 配置Cache系统配置文件
     * </pre>
     */
    void setConfigFile();

    /**
     * 重新载入Cache配置文件
     *
     * @param configFile
     */
    void reload(String configFile);

    /**
     * 获取Cache客户端
     *
     * @return
     */
    T getCacheClient();

    /**
     * <pre>
     * 初始化CACHE信息
     * </pre>
     */
    void InitConfig();
}
