package com.sfsctech.security.jwt;

import com.alibaba.fastjson.JSONObject;

/**
 * Class Playload
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class Playload {

    // jwt签发者
    private String iss;
    // jwt所面向的用户
    private String sub;
    // 接收jwt的一方
    private String aud;
    // jwt的签发时间
    private Long iat;
    // jwt的过期时间，这个过期时间必须要大于签发时间
    private Long exp;
    // 定义在什么时间之前，该jwt都是不可用的.
    private Long nbf;
    // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
    private Object jti;
    // 公共声明
    private JSONObject publicStatement;
    // 私有声明
    private JSONObject privateStatement;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getNbf() {
        return nbf;
    }

    public void setNbf(Long nbf) {
        this.nbf = nbf;
    }

    public Object getJti() {
        return jti;
    }

    public void setJti(Object jti) {
        this.jti = jti;
    }

    public JSONObject getPublicStatement() {
        return publicStatement;
    }

    public void setPublicStatement(JSONObject publicStatement) {
        this.publicStatement = publicStatement;
    }

    public JSONObject getPrivateStatement() {
        return privateStatement;
    }

    public void setPrivateStatement(JSONObject privateStatement) {
        this.privateStatement = privateStatement;
    }
}
