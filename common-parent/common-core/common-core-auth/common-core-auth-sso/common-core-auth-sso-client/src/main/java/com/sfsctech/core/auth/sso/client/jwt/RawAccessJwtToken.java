package com.sfsctech.core.auth.sso.client.jwt;

import com.sfsctech.core.auth.base.sso.exceptions.JwtExpiredTokenException;
import com.sfsctech.core.auth.base.sso.jwt.JwtToken;
import com.sfsctech.core.auth.base.sso.properties.JwtProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

public class RawAccessJwtToken implements JwtToken {

    private Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    private String jwt;
    private JwtProperties settings;

    public RawAccessJwtToken(String jwt, JwtProperties settings) {
        this.jwt = jwt;
        this.settings = settings;
    }

    private Key getKey(String salt) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(salt);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public Jws<Claims> parseClaims() {
        try {
            return Jwts.parser().requireIssuer(settings.getIssuer()).setSigningKey(getKey(settings.getSalt())).parseClaimsJws(jwt);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT jwt: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return jwt;
    }
}
