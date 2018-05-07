//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.resolver;

import com.bestv.common.lang.request.BaseRequest;
import com.bestv.common.net.annotation.CommonNets;
import com.bestv.common.net.domain.CommonServicePointInfo;
import com.bestv.common.net.domain.InterfaceInfo;
import com.bestv.common.net.domain.ServicePointInfo;
import com.bestv.common.net.ex.ArgumentStyleNotAllowException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class SpringHttpInterfaceResolver implements InterfaceResolver<Class> {
    private static final String SERVICE_POINT_PREFIX = "http://";
    private static final String SERVICE_PATH_SEP = "/";

    public SpringHttpInterfaceResolver() {
    }

    public InterfaceInfo parse(Class sourceInterfaceInfo) {
        if (sourceInterfaceInfo == null) {
            return null;
        } else {
            InterfaceInfo interfaceInfo = new InterfaceInfo();
            interfaceInfo.setInterfaceClass(sourceInterfaceInfo);
            interfaceInfo.setAppName(this.getAppName(sourceInterfaceInfo));
            interfaceInfo.setServicePointInfos(this.getServicePointInfos(sourceInterfaceInfo));
            return interfaceInfo;
        }
    }

    private List<ServicePointInfo> getServicePointInfos(Class sourceInterfaceInfo) {
        List<ServicePointInfo> servicePointInfos = new ArrayList();
        String appName = this.getAppName(sourceInterfaceInfo);
        Method[] var4 = sourceInterfaceInfo.getDeclaredMethods();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method service = var4[var6];
            Class[] argumentTypes = service.getParameterTypes();
            if (argumentTypes.length != 1) {
                throw new ArgumentStyleNotAllowException(sourceInterfaceInfo, service);
            }

            Class argumentType = argumentTypes[0];
            if (!BaseRequest.class.isAssignableFrom(argumentType)) {
                throw new ArgumentStyleNotAllowException(sourceInterfaceInfo, service);
            }

            ServicePointInfo servicePointInfo = new CommonServicePointInfo();
            servicePointInfo.setInterfaceClass(sourceInterfaceInfo);
            servicePointInfo.setServiceClientPoint(service);
            servicePointInfo.setArgumentType(argumentType);
            servicePointInfo.setResultType(service.getReturnType());
            servicePointInfo.setServiceServerPoint(this.buildServicePoint(appName, this.getServiceRootPath(sourceInterfaceInfo), this.getServiceRelativePath(service)));
            servicePointInfos.add(servicePointInfo);
        }

        return servicePointInfos;
    }

    private String getAppName(Class sourceInterfaceInfo) {
        String appName = null;
        if (sourceInterfaceInfo.isAnnotationPresent(CommonNets.class)) {
            CommonNets commonNetAnnotation = (CommonNets)sourceInterfaceInfo.getAnnotation(CommonNets.class);
            appName = commonNetAnnotation.appName();
        }

        return appName;
    }

    private String getServiceRootPath(Class sourceInterfaceInfo) {
        String serviceRootPath = null;
        if (sourceInterfaceInfo.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMappingAnnotation = (RequestMapping)sourceInterfaceInfo.getAnnotation(RequestMapping.class);
            serviceRootPath = this.getRequestMappingPath(requestMappingAnnotation);
        }

        return serviceRootPath;
    }

    private String getServiceRelativePath(Method service) {
        String serviceRelativePath = null;
        if (service.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMappingAnnotation = (RequestMapping)service.getAnnotation(RequestMapping.class);
            serviceRelativePath = this.getRequestMappingPath(requestMappingAnnotation);
        } else if (service.isAnnotationPresent(PostMapping.class)) {
            PostMapping postMappingAnnotation = (PostMapping)service.getAnnotation(PostMapping.class);
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
