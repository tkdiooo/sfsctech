package com.sfsctech.core.auth.sso.client.provider;

import com.sfsctech.core.auth.base.sso.constants.SSOConstants;
import com.sfsctech.core.auth.base.sso.exceptions.JwtExpiredTokenException;
import com.sfsctech.core.auth.sso.client.jwt.JwtAuthenticationToken;
import com.sfsctech.core.auth.sso.client.jwt.RawAccessJwtToken;
import com.sfsctech.core.base.constants.DateConstants;
import com.sfsctech.support.common.util.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims();
        Date refresh = DateUtil.getDateSubCondition(jwsClaims.getBody().getExpiration(), DateConstants.DateType.Minute, (int) (rawAccessToken.getSettings().getExpiration().toMinutes() / 2));
        if (DateUtil.compareDate(DateUtil.getCurrentDate(), refresh) > 0) {
            System.out.println("刷新token");
        }
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get(SSOConstants.JWT_CLAIMS_SCOPES, List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User context = new User(subject, "", authorities);
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
