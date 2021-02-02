package com.sfsctech.core.spring.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.core.base.json.FastJsonFilter;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.core.spring.resource.DSMessageSource;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.support.dozer.config.DozerMapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
@Slf4j
@Configuration
@Import({SpringContextUtil.class, TomcatConfig.class, ApplicationInitialize.class, DozerMapperConfig.class})
public class SpringConfig {

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
                SerializerFeature.PrettyFormat,
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteEnumUsingToString
        );
        // TODO 需要动态添加过滤器代码
        fastJsonConfig.setSerializeFilters(new FastJsonFilter());
        fastJsonConfig.setSerializeConfig(FastJson.getSerializeConfig());
        fastConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        return fastConverter;
    }

    /**
     * 配置properties国际化资源
     *
     * @return MessageSource
     */
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(CommonConstants.RESOURCES_I18N_PATH);
        messageSource.setDefaultEncoding(LabelConstants.UTF8);
        // 缓存时间(秒)
        messageSource.setCacheSeconds(600);
        return messageSource;
    }

    /**
     * 配置数据库国际化资源
     *
     * @return MessageSource
     */
    @Bean(name = "dsMessageSource")
    public DSMessageSource dsMessageSource() {
        return new DSMessageSource();
    }
}
