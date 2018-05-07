//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.dto.token;

import java.io.Serializable;

public class Payload implements Serializable {
    private static final long serialVersionUID = 4277328163296127608L;
    private String jsonWebTokenId;
    private String issuer;
    private String subjectId;
    private String audience;
    private String issuedTime;
    private String expires;
    private String invalidBeforeTime;

    public Payload() {
    }

    public String getJsonWebTokenId() {
        return this.jsonWebTokenId;
    }

    public void setJsonWebTokenId(String jsonWebTokenId) {
        this.jsonWebTokenId = jsonWebTokenId;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getAudience() {
        return this.audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getIssuedTime() {
        return this.issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    public String getExpires() {
        return this.expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getInvalidBeforeTime() {
        return this.invalidBeforeTime;
    }

    public void setInvalidBeforeTime(String invalidBeforeTime) {
        this.invalidBeforeTime = invalidBeforeTime;
    }
}
