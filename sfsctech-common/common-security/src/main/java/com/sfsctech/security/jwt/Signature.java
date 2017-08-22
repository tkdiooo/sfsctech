package com.sfsctech.security.jwt;

/**
 * Class Signature
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class Signature {

    private String secret;

    private Header header;

    private Playload playload;

    public Signature(Header header, Playload playload) {
        this.header = header;
        this.playload = playload;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Playload getPlayload() {
        return playload;
    }

    public void setPlayload(Playload playload) {
        this.playload = playload;
    }

    @Override
    public String toString() {
        return "";
    }
}
