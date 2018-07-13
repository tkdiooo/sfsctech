package com.sfsctech.demo.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Class OAuth2AuthorizationConfig
 *
 * @author 张麒 2018-7-13.
 * @version Description:
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizeConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置客户端信息, 用于client认证
        clients.inMemory()
                .withClient("clientapp")
                .secret("112233")
                .redirectUris("http://localhost:9001/callback")
                // 授权码模式
                .authorizedGrantTypes("authorization_code")
                .scopes("read_userinfo", "read_contacts");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // redis保存token
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //允许表单认证
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
