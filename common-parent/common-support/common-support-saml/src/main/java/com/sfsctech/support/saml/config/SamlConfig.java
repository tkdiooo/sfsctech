package com.sfsctech.support.saml.config;

import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.xmlsec.config.impl.JavaCryptoValidationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Provider;
import java.security.Security;

/**
 * Class DubboxConfigurer
 *
 * @author 张麒 2018-1-2.
 * @version Description:
 */
@Configuration
public class SamlConfig {

    private static Logger logger = LoggerFactory.getLogger(SamlConfig.class);

    @Bean
    public Object initSpQueue() {
        initSaml();
        return new Object();
    }

    private void initSaml() {
        JavaCryptoValidationInitializer javaCryptoValidationInitializer = new JavaCryptoValidationInitializer();
        try {
            //这个方法应该在OpenSAML初始化之前被调用，
            //来确保当前的JCE环境可以符合要求：AES/CBC/ISO10126Padding
            // 对于XML的加密，JCE需要支持ACE（128/256），并使用ISO10126Padding（填充位）
            javaCryptoValidationInitializer.init();
        } catch (InitializationException e) {
            e.printStackTrace();
        }

        //打印当前已经被安装的所有JCE的provider
        for (Provider jceProvider : Security.getProviders()) {
            logger.info(jceProvider.getInfo());
        }

        try {
            logger.info("Initializing");
            //正式初始化ＳＡＭＬ服务
            InitializationService.initialize();
        } catch (InitializationException e) {
            throw new RuntimeException("Initialization failed");
        }
    }

}
