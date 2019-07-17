package com.sfsctech.support.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class RawRefreshJwt {

    private final Jws<Claims> claims;

    public RawRefreshJwt(Jws<Claims> claims) {
        this.claims = claims;
    }

    public Jws<Claims> getClaims() {
        return this.claims;
    }

    public String getUsername() {
        return this.claims.getBody().getSubject();
    }
}
