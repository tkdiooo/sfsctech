package com.sfsctech.support.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class RawAccessJwt {

    private Jws<Claims> claims;

    public RawAccessJwt(Jws<Claims> claims) {
        this.claims = claims;
    }

    public Jws<Claims> getClaims() {
        return this.claims;
    }

}
