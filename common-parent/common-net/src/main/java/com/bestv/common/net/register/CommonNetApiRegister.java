//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.register;

import com.bestv.common.net.annotation.CommonNetApiScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 接口注册
 */
public class CommonNetApiRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    private static final Class SCAN_ANNOTATION_CLASS = CommonNetApiScan.class;
    private static final String BASE_PACKAGES_KEY = "basePackages";

    public CommonNetApiRegister() {
    }

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        if (annotationMetadata.hasAnnotation(SCAN_ANNOTATION_CLASS.getName())) {
            String[] basePackages = (String[]) annotationMetadata.getAnnotationAttributes(SCAN_ANNOTATION_CLASS.getName()).get("basePackages");
            CommonNetApiScanner commonNetApiScanner = new CommonNetApiScanner(beanDefinitionRegistry);
            if (this.resourceLoader != null) {
                commonNetApiScanner.setResourceLoader(this.resourceLoader);
            }

            commonNetApiScanner.registerIncludeFilters();
            commonNetApiScanner.doScan(basePackages);
        }
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
