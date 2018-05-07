//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.execute.handler;

import com.bestv.common.dto.EnvContext;
import com.bestv.common.dto.TraceInfo;
import com.bestv.common.lang.request.BaseRequest;
import com.bestv.common.lang.result.BaseResult;
import com.bestv.common.net.domain.InterfaceInfo;
import com.bestv.common.net.domain.ServicePointInfo;
import com.bestv.common.net.ex.HttpExecuteErrorException;
import com.bestv.common.net.ex.InterfaceInfoInValidException;
import com.bestv.common.net.log.CommonNetLogger;
import com.bestv.common.net.log.LoggerTypeEnum;
import com.bestv.common.net.log.factory.CommonNetLoggerFactory;
import com.bestv.common.net.log.factory.GenericCommonNetLoggerFactory;
import com.bestv.common.net.trace.CommonTraceInfoGenerator;
import com.bestv.common.net.trace.TraceInfoGenerator;
import com.bestv.common.net.trace.TraceInfoHolder;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpringHttpInterfaceExecuteHandler implements ExecuteHandler {
    private static final CommonNetLoggerFactory<Class> LOGGER_FACTORY = new GenericCommonNetLoggerFactory();
    private final CommonNetLogger logger;
    private Map<Method, ServicePointInfo> methodInterfaceInfoMap;
    private RestTemplate httpClient;
    private final HttpHeaders httpHeaders;
    private final TraceInfoGenerator traceInfoGenerator = new CommonTraceInfoGenerator();

    public SpringHttpInterfaceExecuteHandler(InterfaceInfo interfaceInfo, RestTemplate httpClient) {
        this.checkInterfaceInfoValid(interfaceInfo);
        this.logger = LOGGER_FACTORY.getLogger(interfaceInfo.getInterfaceClass(), LoggerTypeEnum.CLIENT);
        this.methodInterfaceInfoMap = this.convertToClientPointInterfaceInfoMap(interfaceInfo.getServicePointInfos());
        this.httpClient = httpClient;
        this.httpHeaders = this.buildCommonHttpHeaders();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ServicePointInfo servicePointInfo = (ServicePointInfo)this.methodInterfaceInfoMap.get(method);
        BaseRequest request = (BaseRequest)args[0];
        EnvContext envContext = request.getEnvContext();
        if (envContext == null) {
            envContext = new EnvContext();
            request.setEnvContext(envContext);
        }

        TraceInfo traceInfo = TraceInfoHolder.get();
        if (traceInfo == null) {
            traceInfo = this.traceInfoGenerator.generate();
            TraceInfoHolder.set(traceInfo);
        }

        TraceInfo stableTraceInfo = new TraceInfo();
        stableTraceInfo.setTraceId(traceInfo.getTraceId());
        stableTraceInfo.setSpanId(traceInfo.getSpanId());
        traceInfo = this.traceInfoGenerator.generate(traceInfo);
        envContext.setTraceInfo(traceInfo);
        TraceInfoHolder.set(traceInfo);
        this.logger.logRequest(request);
        HttpEntity httpEntity = new HttpEntity(request, this.httpHeaders);
        ResponseEntity responseEntity = this.httpClient.postForEntity(servicePointInfo.getServiceServerPoint(), httpEntity, servicePointInfo.getResultType(), new Object[0]);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            this.logger.warn(responseEntity.toString());
            throw new HttpExecuteErrorException(httpEntity);
        } else {
            BaseResult result = (BaseResult)responseEntity.getBody();
            EnvContext resultEnvContext = result.getEnvContext();
            if (resultEnvContext == null) {
                resultEnvContext = new EnvContext();
                result.setEnvContext(resultEnvContext);
            }

            resultEnvContext.setTraceInfo(stableTraceInfo);
            this.logger.logResult(result);
            return result;
        }
    }

    private HttpHeaders buildCommonHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Collections.singletonList(Charset.forName("utf-8")));
        return headers;
    }

    private Map<Method, ServicePointInfo> convertToClientPointInterfaceInfoMap(List<ServicePointInfo> servicePointInfos) {
        if (CollectionUtils.isEmpty(servicePointInfos)) {
            return null;
        } else {
            Map<Method, ServicePointInfo> methodInterfaceInfoMap = new HashMap();
            Iterator var3 = servicePointInfos.iterator();

            while(var3.hasNext()) {
                ServicePointInfo servicePointInfo = (ServicePointInfo)var3.next();
                methodInterfaceInfoMap.put(servicePointInfo.getServiceClientPoint(), servicePointInfo);
            }

            return Collections.unmodifiableMap(methodInterfaceInfoMap);
        }
    }

    private void checkInterfaceInfoValid(InterfaceInfo interfaceInfo) {
        this.checkObjectNotEmpty(interfaceInfo);
        this.checkObjectNotEmpty(interfaceInfo.getAppName());
        this.checkObjectNotEmpty(interfaceInfo.getInterfaceClass());
        if (CollectionUtils.isNotEmpty(interfaceInfo.getServicePointInfos())) {
            this.checkServicePointInfosValid(interfaceInfo.getServicePointInfos());
        }

    }

    private void checkServicePointInfosValid(List<ServicePointInfo> servicePointInfos) {
        if (!CollectionUtils.isEmpty(servicePointInfos)) {
            Class interfaceClass = null;
            Iterator var3 = servicePointInfos.iterator();

            while(var3.hasNext()) {
                ServicePointInfo servicePointInfo = (ServicePointInfo)var3.next();
                this.checkObjectNotEmpty(servicePointInfo);
                this.checkObjectNotEmpty(servicePointInfo.getInterfaceClass());
                this.checkObjectNotEmpty(servicePointInfo.getResultType());
                this.checkObjectNotEmpty(servicePointInfo.getServiceClientPoint());
                this.checkObjectNotEmpty(servicePointInfo.getServiceServerPoint());
                this.checkObjectNotEmpty(servicePointInfo.getArgumentType());
                if (!BaseRequest.class.isAssignableFrom(servicePointInfo.getArgumentType())) {
                    throw new InterfaceInfoInValidException();
                }

                if (!BaseResult.class.isAssignableFrom(servicePointInfo.getResultType())) {
                    throw new InterfaceInfoInValidException();
                }

                if (interfaceClass == null) {
                    interfaceClass = servicePointInfo.getInterfaceClass();
                } else if (interfaceClass != servicePointInfo.getInterfaceClass()) {
                    throw new InterfaceInfoInValidException();
                }
            }

        }
    }

    private void checkObjectNotEmpty(Object object) {
        if (object == null) {
            throw new InterfaceInfoInValidException();
        } else if (object instanceof String && StringUtils.isBlank((CharSequence)object)) {
            throw new InterfaceInfoInValidException();
        }
    }
}
