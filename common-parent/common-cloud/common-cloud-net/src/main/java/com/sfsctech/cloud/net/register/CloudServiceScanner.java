//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.cloud.net.register;

import com.sfsctech.cloud.net.execute.factory.InterfaceProxyFactoryBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

public class CloudServiceScanner extends ClassPathBeanDefinitionScanner {
    private static final String INTERFACE_TYPE_FIELD_NAME = "interfaceType";

    public CloudServiceScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (!CollectionUtils.isEmpty(beanDefinitionHolders)) {
            this.processBeanDefinitions(beanDefinitionHolders);
        }

        return beanDefinitionHolders;
    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    public void registerIncludeFilters() {
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> metadataReader.getClassMetadata().isInterface());
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        beanDefinitions.forEach(holder -> {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String interfaceTypeName = definition.getBeanClassName();
            definition.getConstructorArgumentValues().addGenericArgumentValue(interfaceTypeName);
            definition.setBeanClass(InterfaceProxyFactoryBean.class);
            definition.getPropertyValues().add(INTERFACE_TYPE_FIELD_NAME, interfaceTypeName);
            definition.setAutowireMode(2);
        });
    }
}
