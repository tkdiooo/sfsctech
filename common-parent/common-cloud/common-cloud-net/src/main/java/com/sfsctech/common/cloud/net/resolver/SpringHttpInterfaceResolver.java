//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.cloud.net.resolver;

import com.sfsctech.common.cloud.net.annotation.CloudService;
import com.sfsctech.common.cloud.net.domain.ServiceInterface;
import com.sfsctech.common.cloud.net.domain.ServiceInterfacePoint;
import com.sfsctech.common.cloud.net.ex.ParameterLengthException;
import com.sfsctech.common.cloud.net.ex.ParameterStyleException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpringHttpInterfaceResolver implements InterfaceResolver<Class> {
    private static final String SERVICE_POINT_PREFIX = "http://";
    private static final String SERVICE_PATH_SEP = "/";

    /**
     *
     * @param interfaceClass
     * @return
     */
    public ServiceInterface parse(Class interfaceClass) {
        if (interfaceClass == null) {
            return null;
        }
        ServiceInterface interfaceInfo = new ServiceInterface();
        interfaceInfo.setInterfaceClass(interfaceClass);
        interfaceInfo.setAppName(this.getAppName(interfaceClass));
        interfaceInfo.setServiceInterfacePoint(this.getServiceInterfacePoint(interfaceClass));
        return interfaceInfo;
    }

    private List<ServiceInterfacePoint> getServiceInterfacePoint(Class interfaceClass) {
        List<ServiceInterfacePoint> servicePointInfos = new ArrayList<>();
        String appName = this.getAppName(interfaceClass);
        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method method : methods) {
            Class[] argumentTypes = method.getParameterTypes();
            if (argumentTypes.length != 1) {
                throw new ParameterLengthException(interfaceClass, method);
            }

            Class argumentType = argumentTypes[0];
            if (!BaseRequest.class.isAssignableFrom(argumentType)) {
                throw new ParameterStyleException(interfaceClass, method);
            }

            ServicePointInfo servicePointInfo = new CommonServicePointInfo();
            servicePointInfo.setInterfaceClass(interfaceClass);
            servicePointInfo.setServiceClientPoint(service);
            servicePointInfo.setArgumentType(argumentType);
            servicePointInfo.setResultType(service.getReturnType());
            servicePointInfo.setServiceServerPoint(this.buildServicePoint(appName, this.getServiceRootPath(interfaceClass), this.getServiceRelativePath(service)));
            servicePointInfos.add(servicePointInfo);
        }

        return servicePointInfos;
    }

    private String getAppName(Class interfaceClass) {
        if (interfaceClass.isAnnotationPresent(CloudService.class)) {
            CloudService annotation = (CloudService) interfaceClass.getAnnotation(CloudService.class);
            return annotation.value();
        }
        return "";
    }

    private String getServiceRootPath(Class sourceInterfaceInfo) {
        String serviceRootPath = null;
        if (sourceInterfaceInfo.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMappingAnnotation = (RequestMapping) sourceInterfaceInfo.getAnnotation(RequestMapping.class);
            serviceRootPath = this.getRequestMappingPath(requestMappingAnnotation);
        }

        return serviceRootPath;
    }

    private String getServiceRelativePath(Method service) {
        String serviceRelativePath = null;
        if (service.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMappingAnnotation = (RequestMapping) service.getAnnotation(RequestMapping.class);
            serviceRelativePath = this.getRequestMappingPath(requestMappingAnnotation);
        } else if (service.isAnnotationPresent(PostMapping.class)) {
            PostMapping postMappingAnnotation = (PostMapping) service.getAnnotation(PostMapping.class);
            serviceRelativePath = this.getPostMappingPath(postMappingAnnotation);
        }

        if (StringUtils.isBlank(serviceRelativePath)) {
            serviceRelativePath = service.getName();
        }

        return serviceRelativePath;
    }

    private String buildServicePoint(String appName, String serviceRootPath, String relativePath) {
        if (StringUtils.isBlank(serviceRootPath)) {
            serviceRootPath = "";
        }

        if (StringUtils.isBlank(relativePath)) {
            relativePath = "";
        }

        StringBuilder servicePoint = new StringBuilder("http://");
        servicePoint.append(appName).append("/").append(serviceRootPath).append("/").append(relativePath);
        return servicePoint.toString();
    }

    private String getRequestMappingPath(RequestMapping requestMappingAnnotation) {
        if (requestMappingAnnotation == null) {
            return null;
        } else {
            String[] serviceRootPaths = requestMappingAnnotation.path();
            if (serviceRootPaths.length == 0) {
                serviceRootPaths = requestMappingAnnotation.value();
            }

            return serviceRootPaths[0];
        }
    }

    private String getPostMappingPath(PostMapping postMappingAnnotation) {
        if (postMappingAnnotation == null) {
            return null;
        } else {
            String[] serviceRootPaths = postMappingAnnotation.path();
            if (serviceRootPaths.length == 0) {
                serviceRootPaths = postMappingAnnotation.value();
            }

            return serviceRootPaths[0];
        }
    }
}
