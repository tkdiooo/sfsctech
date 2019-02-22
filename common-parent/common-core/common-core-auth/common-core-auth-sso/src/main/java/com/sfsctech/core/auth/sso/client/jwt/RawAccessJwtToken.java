package com.sfsctech.core.auth.sso.client.jwt;

import com.sfsctech.core.auth.sso.common.exceptions.JwtExpiredTokenException;
import com.sfsctech.core.auth.sso.common.jwt.JwtToken;
import com.sfsctech.core.auth.sso.common.jwt.JwtTokenFactory;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessJwtToken implements JwtToken {

    private Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    private String jwt;
    private JwtTokenFactory jwtTokenFactory;

    public RawAccessJwtToken(String jwt, JwtTokenFactory jwtTokenFactory) {
        this.jwt = jwt;
        this.jwtTokenFactory = jwtTokenFactory;
    }

    public Jws<Claims> parseClaims() {
        try {
            return jwtTokenFactory.parseJWT(this.jwt);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT jwt: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getJwt() {
        return jwt;
    }
}
