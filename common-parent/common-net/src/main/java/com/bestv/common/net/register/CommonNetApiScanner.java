//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.register;

import com.bestv.common.net.execute.factory.InterfaceProxyFactoryBean;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class CommonNetApiScanner extends ClassPathBeanDefinitionScanner {
    private static final String INTERFACE_TYPE_FIELD_NAME = "interfaceType";

    public CommonNetApiScanner(BeanDefinitionRegistry registry) {
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
        this.addIncludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return metadataReader.getClassMetadata().isInterface();
            }
        });
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        Iterator var3 = beanDefinitions.iterator();

        while(var3.hasNext()) {
            BeanDefinitionHolder holder = (BeanDefinitionHolder)var3.next();
            GenericBeanDefinition definition = (GenericBeanDefinition)holder.getBeanDefinition();
            String interfaceTypeName = definition.getBeanClassName();
            definition.getConstructorArgumentValues().addGenericArgumentValue(interfaceTypeName);
            definition.setBeanClass(InterfaceProxyFactoryBean.class);
            definition.getPropertyValues().add("interfaceType", interfaceTypeName);
            definition.setAutowireMode(2);
        }

    }
}
