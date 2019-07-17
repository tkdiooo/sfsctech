package com.sfsctech.core.auth.sso.client.jwt;

import com.sfsctech.support.jwt.token.RawAccessJwt;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *
 */
public class JwtAuthenticationToken<T> extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private RawAccessJwt rawAccessJwt;
    private T user;

    public JwtAuthenticationToken(RawAccessJwt unsafeToken) {
        super(null);
        this.rawAccessJwt = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(T user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.user = user;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessJwt;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.rawAccessJwt = null;
    }
}
