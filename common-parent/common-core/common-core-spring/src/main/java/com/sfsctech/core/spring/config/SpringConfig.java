package com.sfsctech.core.spring.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.core.spring.util.SpringContextUtil;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Class SpringConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Import({SpringContextUtil.class, TomcatConfig.class, ApplicationInitialize.class})
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

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastJsonConfig.setSerializeConfig(FastJson.getSerializeConfig());
        fastConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        return fastConverter;
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
