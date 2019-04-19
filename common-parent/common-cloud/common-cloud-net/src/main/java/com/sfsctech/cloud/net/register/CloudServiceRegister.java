//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.cloud.net.register;

import com.sfsctech.cloud.net.starter.EnableCloudServer;
import com.sfsctech.support.common.util.ArrayUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * 接口注册
 */
public class CloudServiceRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    private static final Class SCAN_ANNOTATION_CLASS = EnableCloudServer.class;
    private static final String BASE_PACKAGES_KEY = "packages";
    private static final String DEFAULT_PACKAGES = "com.sfsctech.*.inf";

    @Override
    public void registerBeanDefinitions(@Nullable @NotNull AnnotationMetadata annotationMetadata, @Nullable @NotNull BeanDefinitionRegistry beanDefinitionRegistry) {
        if (annotationMetadata.hasAnnotation(SCAN_ANNOTATION_CLASS.getName())) {
            String[] basePackages = ArrayUtil.add((String[]) annotationMetadata.getAnnotationAttributes(SCAN_ANNOTATION_CLASS.getName()).get(BASE_PACKAGES_KEY), DEFAULT_PACKAGES);
            if (ArrayUtil.isNotEmpty(basePackages)) {
                CloudServiceScanner commonNetApiScanner = new CloudServiceScanner(beanDefinitionRegistry);
                if (this.resourceLoader != null) commonNetApiScanner.setResourceLoader(this.resourceLoader);
                commonNetApiScanner.registerIncludeFilters();
                commonNetApiScanner.doScan(basePackages);
            }
        }
    }

    @Override
    public void setResourceLoader(@Nullable @NotNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
