package com.sfsctech.common.core.spring.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.sfsctech.common.core.base.constants.CommonConstants;
import com.sfsctech.common.core.base.constants.LabelConstants;
import com.sfsctech.common.core.base.constants.RpcConstants;
import com.sfsctech.common.core.spring.util.SpringContextUtil;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Class SpringConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
// TODO
@Import(SpringContextUtil.class)
public class SpringConfig {

    private final Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    /**
     * 验证工厂类
     *
     * @param messageSource
     * @return
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * FastJson Enum处理
     *
     * @return
     */
    @Bean
    @SuppressWarnings("unchecked")
    public SerializeConfig serializeConfig() {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.configEnumAsJavaBean(RpcConstants.Status.class);
        return serializeConfig;
    }

    /**
     * 添加国际化资源<br>
     * 静态方法保证其在系统启动时就调用
     *
     * @return MessageSource
     */
    @Bean
    public static MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(CommonConstants.RESOURCES_I18N_PATH);
        messageSource.setDefaultEncoding(LabelConstants.UTF8);
        // 缓存时间(秒)
        messageSource.setCacheSeconds(600);
        return messageSource;
    }
}
