package com.sfsctech.core.auth.sso.client.provider;

import com.sfsctech.core.auth.sso.base.constants.SSOConstants;
import com.sfsctech.core.auth.sso.base.inf.SSOInterface;
import com.sfsctech.core.auth.sso.client.jwt.JwtAuthenticationToken;
import com.sfsctech.support.jwt.token.RawAccessJwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final SSOInterface service;

    public JwtAuthenticationProvider(SSOInterface service) {
        this.service = service;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwt rawAccessToken = (RawAccessJwt) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.getClaims();

//            try {
//                return Jwts.parser().requireIssuer(settings.getIssuer()).setSigningKey(getKey(settings.getSalt())).parseClaimsJws(jwt);
//            } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
//                logger.error("Invalid JWT Token", ex);
//                throw new BadCredentialsException("Invalid JWT jwt: ", ex);
//            } catch (ExpiredJwtException expiredEx) {
//                logger.info("JWT Token is expired", expiredEx);
//                throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
//            }

        // 获取刷新时间
//        Date refresh = DateUtil.getDateSubCondition(jwsClaims.getBody().getExpiration(), DateConstants.DateType.Minute, (int) (rawAccessToken.getSettings().getExpiration().toMinutes() / 2));
        // 如果系统当前时间大于刷新时间
//        if (DateUtil.compareDate(DateUtil.getCurrentDate(), refresh) > 0) {
//            System.out.println(service);
//            System.out.println("刷新token");
//        }
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get(SSOConstants.JWT_CLAIMS_SCOPES, List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User context = new User(subject, "", authorities);
        return new JwtAuthenticationToken<>(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
