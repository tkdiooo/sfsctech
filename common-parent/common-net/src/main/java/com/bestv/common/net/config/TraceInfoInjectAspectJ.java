//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import com.bestv.common.dto.EnvContext;
import com.bestv.common.dto.TraceInfo;
import com.bestv.common.lang.request.BaseRequest;
import com.bestv.common.lang.result.BaseResult;
import com.bestv.common.net.log.CommonNetLogger;
import com.bestv.common.net.log.LoggerTypeEnum;
import com.bestv.common.net.log.factory.CommonNetLoggerFactory;
import com.bestv.common.net.log.factory.GenericCommonNetLoggerFactory;
import com.bestv.common.net.trace.CommonTraceInfoGenerator;
import com.bestv.common.net.trace.TraceInfoGenerator;
import com.bestv.common.net.trace.TraceInfoHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
class TraceInfoInjectAspectJ {
    private static final CommonNetLoggerFactory<Class> LOGGER_FACTORY = new GenericCommonNetLoggerFactory();
    private final TraceInfoGenerator traceInfoGenerator = new CommonTraceInfoGenerator();

    TraceInfoInjectAspectJ() {
    }

    @Pointcut("within(*..common.api.*+) && execution(com.bestv.common.lang.result.BaseResult+ *..biz.impl.*.*(com.bestv.common.lang.request.BaseRequest+))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Class interfaceClass = joinPoint.getSignature().getDeclaringType().getInterfaces()[0];
        CommonNetLogger logger = LOGGER_FACTORY.getLogger(interfaceClass, LoggerTypeEnum.SERVER);
        BaseRequest request = (BaseRequest)joinPoint.getArgs()[0];
        EnvContext envContext = request.getEnvContext();
        if (envContext == null) {
            envContext = new EnvContext();
            request.setEnvContext(envContext);
        }

        TraceInfo traceInfo = envContext.getTraceInfo();
        if (traceInfo == null) {
            traceInfo = this.traceInfoGenerator.generate();
            request.getEnvContext().setTraceInfo(traceInfo);
        } else {
            this.traceInfoGenerator.load(traceInfo);
        }

        logger.logRequest(request);
        TraceInfo stableTraceInfo = new TraceInfo();
        stableTraceInfo.setTraceId(traceInfo.getTraceId());
        stableTraceInfo.setSpanId(traceInfo.getSpanId());
        TraceInfoHolder.set(traceInfo);
        BaseResult result = (BaseResult)joinPoint.proceed();
        EnvContext resultEnvContext = result.getEnvContext();
        if (resultEnvContext == null) {
            resultEnvContext = new EnvContext();
            result.setEnvContext(resultEnvContext);
        }

        resultEnvContext.setTraceInfo(stableTraceInfo);
        TraceInfoHolder.clear();
        this.traceInfoGenerator.unload(traceInfo.getTraceId());
        logger.logResult(result);
        return result;
    }
}
