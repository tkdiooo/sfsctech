package com.sfsctech.configurer;

import com.sfsctech.authorize.base.properties.JwtProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@ComponentScan(basePackageClasses = JwtProperties.class)
public class AuthConfigurer {

}
