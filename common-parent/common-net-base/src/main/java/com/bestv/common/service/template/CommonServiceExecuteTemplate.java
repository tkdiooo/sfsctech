//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.service.template;

import com.bestv.common.lang.enums.CommonScenario;
import com.bestv.common.lang.ex.CommonErrorCode;
import com.bestv.common.lang.ex.CommonException;
import com.bestv.common.lang.ex.ErrorContext;
import com.bestv.common.lang.ex.GenericException;
import com.bestv.common.lang.result.BaseResult;
import com.bestv.common.util.AssertUtil;
import com.bestv.common.util.ErrorContextBuilder;
import com.bestv.common.util.ScenarioHolder;
import com.bestv.common.util.ServiceObjectContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonServiceExecuteTemplate implements ServiceExecuteTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExecuteTemplate.class);

    public CommonServiceExecuteTemplate() {
        LOGGER.info("载入普通业务执行模板实现类成功...");
    }

    public <T extends BaseResult> T executeService(T result, ServiceCallBack serviceCallBack) {
        if (ScenarioHolder.get() == null) {
            ScenarioHolder.set(CommonScenario.COMMON);
        }

        boolean needDoAfterService = false;

        try {
            serviceCallBack.check();
            serviceCallBack.doService();
            if (result.isSuccess()) {
                if (result.getErrorContext() == null) {
                    needDoAfterService = true;
                }
            } else {
                AssertUtil.isNotNull(result.getErrorContext(), CommonErrorCode.UNKNOWN_ERROR, new Object[0]);
                AssertUtil.isNotBlank(result.getErrorContext().getErrorCode(), CommonErrorCode.UNKNOWN_ERROR, new Object[0]);
            }
        } catch (Exception var14) {
            ErrorContext errorContext;
            if (CommonException.class.isAssignableFrom(var14.getClass())) {
                LOGGER.warn("发生了业务异常", var14);
                errorContext = ErrorContextBuilder.buildErrorContext(ScenarioHolder.get(), (CommonException)var14);
            } else {
                LOGGER.error("发生了非预期的异常", var14);
                errorContext = ErrorContextBuilder.buildErrorContext(ScenarioHolder.get(), new GenericException(CommonErrorCode.UNKNOWN_ERROR, var14));
            }

            result.setErrorContext(errorContext);
        } finally {
            if (needDoAfterService) {
                try {
                    serviceCallBack.afterService();
                } catch (Exception var13) {
                    LOGGER.error("执行业务后置操作时发生异常.", var13);
                }
            }

            ServiceObjectContainer.clear();
            ScenarioHolder.clear();
        }

        return result;
    }
}
