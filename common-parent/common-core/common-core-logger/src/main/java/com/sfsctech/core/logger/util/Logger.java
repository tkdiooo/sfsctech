package com.sfsctech.core.logger.util;

import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.core.base.session.SessionHolder;

/**
 * Class loggerUtil
 *
 * @author 张麒 2018-7-9.
 * @version Description:
 */
public class Logger {

    private org.slf4j.Logger logger;
    private String module;

    private Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    private Logger(org.slf4j.Logger logger, String module) {
        this.logger = logger;
        this.module = module;
    }

    public static Logger getInstance(org.slf4j.Logger logger) {
        return new Logger(logger);
    }

    public static Logger getInstance(org.slf4j.Logger logger, String module) {
        return new Logger(logger, module);
    }

    /**
     * 操作日志记录
     *
     * @param module   系统模块名称
     * @param function 系统功能名称
     * @param object   实体参数
     */
    public void logOperate(String module, String function, Object... object) {
        logger.info("用户[" + SessionHolder.getSessionInfo().getUserAuthData().getAccount() + "]" + "操作[" + module + "]模块的[" + function + "]功能, params:[" + FastJson.toJSONString(object) + "]");
    }


    /**
     * 操作日志记录
     *
     * @param function 系统功能名称
     * @param object   实体参数
     */
    public void logOperate(String function, Object... object) {
        logger.info("用户[" + SessionHolder.getSessionInfo().getUserAuthData().getAccount() + "]" + "操作[" + this.module + "]模块的[" + function + "]功能, params:[" + FastJson.toJSONString(object) + "]");
    }
}
