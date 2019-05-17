package com.sfsctech.cloud.net.resolver;

import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.cloud.net.domain.ServiceInterface;
import com.sfsctech.cloud.net.domain.ServiceInterfacePoint;
import com.sfsctech.cloud.net.ex.AppNameNotExistsException;
import com.sfsctech.cloud.net.ex.InterfaceMethodException;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpringHttpInterfaceResolver implements InterfaceResolver<Class> {
    private static final String SERVICE_POINT_PREFIX = "http://";

    public ServiceInterface parse(Class interfaceClass) {
        if (interfaceClass == null) {
            return null;
        }
        ServiceInterface interfaceInfo = new ServiceInterface();
        interfaceInfo.setInterfaceClass(interfaceClass);
        interfaceInfo.setAppName(this.getAppName(interfaceClass));
        interfaceInfo.setServiceInterfacePoint(this.buildServiceInterfacePoint(interfaceClass));
        return interfaceInfo;
    }

    private List<ServiceInterfacePoint> buildServiceInterfacePoint(Class interfaceClass) {
        List<ServiceInterfacePoint> interfacePoints = new ArrayList<>();
        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method method : methods) {
            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new InterfaceMethodException(interfaceClass.getName() + "类的" + method.getName() + "方法含有非法的参数定义, 正确的参数只能有一个对象");
            }

            Class parameterType = parameterTypes[0];
            if (!BaseDto.class.isAssignableFrom(parameterType)) {
                throw new InterfaceMethodException(interfaceClass.getName() + "类的" + method.getName() + "方法含有非法的参数定义, 正确的参数只能是一个继承了" + BaseDto.class.getName() + "类的对象");
            }

            if (!RpcResult.class.equals(method.getReturnType()) && !Void.TYPE.equals(method.getReturnType())) {
                throw new InterfaceMethodException(interfaceClass.getName() + "类的" + method.getName() + "方法含有非法的返回结果定义, 正确的返回结果只能是" + RpcResult.class.getName() + "类的对象");
            }
            ServiceInterfacePoint servicePointInfo = new ServiceInterfacePoint();
            servicePointInfo.setMethod(method);
            servicePointInfo.setParams(parameterType);
            servicePointInfo.setResult(method.getGenericReturnType());
            servicePointInfo.setServiceUrl(this.buildServicePoint(interfaceClass, method));
            interfacePoints.add(servicePointInfo);
        }

        return interfacePoints;
    }


    private String buildServicePoint(Class interfaceClass, Method method) {
        return SERVICE_POINT_PREFIX + getAppName(interfaceClass) + getContextPath(interfaceClass) + getServiceRootPath(interfaceClass) + getServiceRelativePath(method);
    }

    private String getAppName(Class interfaceClass) {
        if (!interfaceClass.isAnnotationPresent(CloudService.class)) {
            throw new AppNameNotExistsException(interfaceClass);
        }
        CloudService annotation = (CloudService) interfaceClass.getAnnotation(CloudService.class);
        if (StringUtil.isNotBlank(annotation.appName())) return annotation.appName() + LabelConstants.FORWARD_SLASH;
        else return annotation.value() + LabelConstants.FORWARD_SLASH;
    }

    private String getContextPath(Class interfaceClass) {
        CloudService annotation = (CloudService) interfaceClass.getAnnotation(CloudService.class);
        if (StringUtil.isNotBlank(annotation.contextPath()))
            return annotation.contextPath() + LabelConstants.FORWARD_SLASH;
        else return "";
    }

    private String getServiceRootPath(Class interfaceClass) {
        if (interfaceClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMappingAnnotation = (RequestMapping) interfaceClass.getAnnotation(RequestMapping.class);
            return getRequestMappingPath(requestMappingAnnotation) + LabelConstants.FORWARD_SLASH;
        }
        return "";
    }

    private String getServiceRelativePath(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
            return getRequestMappingPath(requestMappingAnnotation);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping postMappingAnnotation = method.getAnnotation(PostMapping.class);
            return getPostMappingPath(postMappingAnnotation);
        } else {
            return method.getName();
        }
    }

    private String getRequestMappingPath(RequestMapping requestMapping) {
        if (null != requestMapping) {
            if (requestMapping.path().length == 0)
                return requestMapping.value()[0];
            return requestMapping.path()[0];
        }
        return "";
    }

    private String getPostMappingPath(PostMapping postMapping) {
        if (null != postMapping) {
            if (postMapping.path().length == 0) {
                return postMapping.value()[0];
            }
            return postMapping.path()[0];
        }
        return "";
    }
}
