//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.service.template;

import com.alibaba.fastjson.JSON;
import com.bestv.common.lang.enums.CommonScenario;
import com.bestv.common.lang.ex.CommonErrorCode;
import com.bestv.common.lang.ex.CommonException;
import com.bestv.common.lang.ex.ErrorContext;
import com.bestv.common.lang.ex.GenericException;
import com.bestv.common.lang.result.BaseResult;
import com.bestv.common.util.ErrorContextBuilder;
import com.bestv.common.util.ScenarioHolder;
import com.bestv.common.util.ServiceObjectContainer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HystrixServiceExecuteTemplate implements ServiceExecuteTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExecuteTemplate.class);
    private final String APP_NAME;
    private final Boolean fallbackAble;
    private final Integer maxConcurrent;
    private Setter setter;

    public HystrixServiceExecuteTemplate(String appName) {
        LOGGER.info("载入Hystrix业务执行模板实现类成功...");
        this.APP_NAME = appName;
        this.fallbackAble = true;
        this.maxConcurrent = 20;
        LOGGER.info("使用默认hystrix业务执行配置: {}", JSON.toJSONString(this.getSetter()));
    }

    public HystrixServiceExecuteTemplate(String appName, Setter setter) {
        LOGGER.info("载入Hystrix业务执行模板实现类成功...");
        this.APP_NAME = appName;
        this.fallbackAble = true;
        this.maxConcurrent = 20;
        this.setter = setter;
        LOGGER.info("使用自定义hystrix业务执行配置: {}", JSON.toJSONString(this.getSetter()));
    }

    public HystrixServiceExecuteTemplate(String appName, boolean fallbackAble, Integer maxConcurrent) {
        LOGGER.info("载入Hystrix业务执行模板实现类成功...");
        this.APP_NAME = appName;
        this.fallbackAble = fallbackAble;
        this.maxConcurrent = maxConcurrent;
        LOGGER.info("使用默认hystrix业务执行配置: {}", JSON.toJSONString(this.getSetter()));
    }

    public <T extends BaseResult> T executeService(T result, ServiceCallBack serviceCallBack) {
        if (ScenarioHolder.get() == null) {
            ScenarioHolder.set(CommonScenario.COMMON);
        }

        (new HystrixServiceExecuteTemplate.HystrixCommandExecutor(result, serviceCallBack)).execute();
        return result;
    }

    public Setter getSetter() {
        if (this.setter == null) {
            synchronized(this) {
                if (this.setter == null) {
                    this.setter = Setter.withGroupKey(Factory.asKey(this.APP_NAME)).andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withFallbackEnabled(this.fallbackAble).withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE).withExecutionIsolationSemaphoreMaxConcurrentRequests(this.maxConcurrent).withFallbackIsolationSemaphoreMaxConcurrentRequests(Runtime.getRuntime().availableProcessors() * 2 + 1).withRequestCacheEnabled(false));
                }
            }
        }

        return this.setter;
    }

    private class HystrixCommandExecutor extends HystrixCommand<Void> {
        private CommonScenario scenarioInfo = ScenarioHolder.get();
        private ServiceCallBack serviceCallBack;
        private boolean needSendMessage;
        private BaseResult result;

        public HystrixCommandExecutor(BaseResult baseResult, ServiceCallBack serviceCallBack) {
            super(HystrixServiceExecuteTemplate.this.getSetter().andCommandKey(com.netflix.hystrix.HystrixCommandKey.Factory.asKey(((Enum)ScenarioHolder.get()).name())));
            this.result = baseResult;
            this.serviceCallBack = serviceCallBack;
        }

        protected Void run() throws Exception {
            try {
                this.serviceCallBack.check();
                this.serviceCallBack.doService();
                if (this.result.isSuccess() && this.result.getErrorContext() == null) {
                    this.needSendMessage = true;
                }
            } catch (Exception var11) {
                ErrorContext errorContext;
                if (!CommonException.class.isAssignableFrom(var11.getClass())) {
                    errorContext = ErrorContextBuilder.buildErrorContext(this.scenarioInfo, new GenericException(CommonErrorCode.UNKNOWN_ERROR));
                    this.result.setErrorContext(errorContext);
                    throw var11;
                }

                errorContext = ErrorContextBuilder.buildErrorContext(this.scenarioInfo, (CommonException)var11);
                this.result.setErrorContext(errorContext);
            } finally {
                if (this.needSendMessage) {
                    try {
                        this.serviceCallBack.afterService();
                    } catch (Exception var10) {
                        HystrixServiceExecuteTemplate.LOGGER.error("业务后置逻辑执行异常", var10);
                    }
                }

                ServiceObjectContainer.clear();
                ScenarioHolder.clear();
            }

            return null;
        }

        protected Void getFallback() {
            if (this.serviceCallBack instanceof FallbackableServiceCallBack) {
                try {
                    ((FallbackableServiceCallBack)this.serviceCallBack).fallback();
                    return null;
                } catch (Exception var2) {
                    HystrixServiceExecuteTemplate.LOGGER.error("业务降级失败.", var2);
                    throw new RuntimeException(var2);
                }
            } else {
                throw new RuntimeException(this.getExecutionException());
            }
        }
    }
}
