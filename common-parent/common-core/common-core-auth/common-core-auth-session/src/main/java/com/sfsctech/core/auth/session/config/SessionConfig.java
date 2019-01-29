package com.sfsctech.core.auth.session.config;

import com.sfsctech.core.auth.base.config.BaseWebSecurityConfig;
import com.sfsctech.core.auth.base.config.AuthConfig;
import com.sfsctech.core.auth.session.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@Import(AuthConfig.class)
public class SessionConfig extends BaseWebSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.basicConfigure(http)) {
            FormLoginConfigurer<HttpSecurity> formLogin = http.authorizeRequests().and().formLogin();
            // 自定义登录成功处理
            if (null != config.auth().getLogin().getAuthenticationSuccessHandler()) {
                formLogin.successHandler(config.auth().getLogin().getAuthenticationSuccessHandler().newInstance());
            }
            // 默认登录成功处理
            else {
                formLogin.successHandler(new LoginSuccessHandler(config.getWelcomeFile()));
            }
        }
    }
}
