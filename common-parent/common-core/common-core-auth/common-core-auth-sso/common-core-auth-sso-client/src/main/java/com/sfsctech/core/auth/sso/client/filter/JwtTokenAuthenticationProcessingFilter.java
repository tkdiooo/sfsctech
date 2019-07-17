package com.sfsctech.core.auth.sso.client.filter;

import com.sfsctech.core.auth.sso.base.constants.SSOConstants;
import com.sfsctech.core.auth.sso.base.token.extractor.TokenExtractor;
import com.sfsctech.core.auth.sso.client.jwt.JwtAuthenticationToken;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.jwt.handler.JwtFactory;
import com.sfsctech.support.jwt.token.RawAccessJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationProcessingFilter.class);

    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    private final JwtFactory jwtFactory;
    private CacheFactory<RedisProxy<String, Object>> factory;

    public JwtTokenAuthenticationProcessingFilter(CacheFactory<RedisProxy<String, Object>> factory, AuthenticationFailureHandler failureHandler, TokenExtractor tokenExtractor, JwtFactory jwtFactory, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
        this.jwtFactory = jwtFactory;
        this.factory = factory;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("请求路径:{}", HttpUtil.getFullUrl(request));
        logger.info("token提取类:{}", tokenExtractor.getClass());
        String Access_Jwt_Cache = tokenExtractor.extract(request, response);
        String jwt = factory.get(Access_Jwt_Cache);
        if (StringUtil.isBlank(jwt)) {
            throw new AuthenticationServiceException("用户登录认证失败,Jwt信息为空!");
        } else if (jwt.equals(SSOConstants.JWT_CANCEL_MSG)) {
            throw new AuthenticationServiceException("用户登录认证失败," + SSOConstants.JWT_CANCEL_MSG + "!");
        }
        logger.info("获取JwtToken:{}", jwt);
        RawAccessJwt token = jwtFactory.getRawAccessJwt(jwt);
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
