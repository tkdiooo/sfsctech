package com.sfsctech.demo.oauth.config;

import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.demo.oauth.service.ApplyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Class OAuth2AuthorizationConfig
 *
 * @author 张麒 2018-7-13.
 * @version Description:
 */
@Configuration
@EnableAuthorizationServer
@Import({CacheConfig.class})
public class OAuth2AuthorizeConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private ApplyClientDetailService clientDetailService;

    /*@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("test-secret");
        return converter;
    }*/

    /**
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置客户端信息, 用于client认证
        clients.withClientDetails(clientDetailService);
    }

    /**
     * 配置AuthorizationEndpoint支持的grant type
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 用户认证管理
        endpoints.authenticationManager(authenticationManager)
                // redis保持token
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
        // Jwt保持token
//                .tokenStore(new JwtTokenStore(accessTokenConverter()))
        ;
    }

    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //允许表单认证
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
