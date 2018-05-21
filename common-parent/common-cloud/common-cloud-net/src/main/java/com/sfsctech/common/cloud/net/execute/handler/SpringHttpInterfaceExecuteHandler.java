//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.cloud.net.execute.handler;

import com.sfsctech.common.cloud.net.domain.ServiceInterface;
import com.sfsctech.common.cloud.net.domain.ServiceInterfacePoint;
import com.sfsctech.common.cloud.net.ex.HttpExecuteErrorException;
import com.sfsctech.common.core.base.domain.dto.BaseDto;
import com.sfsctech.common.core.rpc.result.ActionResult;
import com.sfsctech.common.support.util.AssertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringHttpInterfaceExecuteHandler implements ExecuteHandler {
    private Map<Method, ServiceInterfacePoint> pointMap;
    private RestTemplate httpClient;
    private final HttpHeaders httpHeaders;

    private final Logger logger = LoggerFactory.getLogger(SpringHttpInterfaceExecuteHandler.class);

    public SpringHttpInterfaceExecuteHandler(ServiceInterface interfaceInfo, RestTemplate httpClient) {
        checkInterface(interfaceInfo);
        this.pointMap = convertToClientPointMap(interfaceInfo.getServiceInterfacePoint());
        this.httpClient = httpClient;
        this.httpHeaders = buildCommonHttpHeaders();
    }

    /**
     * 执行代理方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        ServiceInterfacePoint servicePointInfo = pointMap.get(method);
        BaseDto request = (BaseDto) args[0];

        logger.info(request.toString());

        ParameterizedTypeReference<ActionResult> typeReference = ParameterizedTypeReference.forType(servicePointInfo.getResult());
        HttpEntity<BaseDto> httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<ActionResult> responseEntity = httpClient.exchange(servicePointInfo.getServiceUrl(), HttpMethod.POST, httpEntity, typeReference);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            logger.warn(responseEntity.toString());
            throw new HttpExecuteErrorException(httpEntity);
        } else {
            ActionResult result = responseEntity.getBody();
            logger.info(result.toString());
            return result;
        }
    }

    private HttpHeaders buildCommonHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Collections.singletonList(Charset.forName("utf-8")));
        return headers;
    }

    private Map<Method, ServiceInterfacePoint> convertToClientPointMap(List<ServiceInterfacePoint> points) {
        if (CollectionUtils.isNotEmpty(points)) {
            Map<Method, ServiceInterfacePoint> map = new HashMap<>();
            points.forEach(point -> map.put(point.getMethod(), point));
            // 返回一个不可增删的map
            return Collections.unmodifiableMap(map);
        }
        return null;
    }

    /**
     * 验证接口
     */
    private void checkInterface(ServiceInterface serviceInterface) {
        // 验证接口class是否为空
        AssertUtil.notNull(serviceInterface.getInterfaceClass(), ServiceInterface.class.getName() + "接口信息错误，InterfaceClass不能为空");
        // 验证接口appname是否为空
        AssertUtil.isNotBlank(serviceInterface.getAppName(), serviceInterface.getInterfaceClass().getName() + "接口信息错误，AppName不能为空");
        if (CollectionUtils.isNotEmpty(serviceInterface.getServiceInterfacePoint())) {
            checkInterfacePoints(serviceInterface.getServiceInterfacePoint());
        }
    }

    /**
     * 验证接口方法
     */
    private void checkInterfacePoints(List<ServiceInterfacePoint> interfacePoints) {
        if (!CollectionUtils.isEmpty(interfacePoints)) {
            interfacePoints.forEach(point -> {
                AssertUtil.notNull(point.getMethod(), "接口信息不合法，方法对象method为空");
                AssertUtil.notNull(point.getResult(), "接口信息不合法，方法返回结果为空");
                AssertUtil.notNull(point.getParams(), "接口信息不合法，方法参数为空");
                AssertUtil.notNull(point.getServiceUrl(), "接口信息不合法，服务Url为空");
            });
        }
    }

}
