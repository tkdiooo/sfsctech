package com.sfsctech.common.support.security.rsa;

import java.math.BigInteger;

/**
 * Class RSAModel
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class RSAModel {

    private BigInteger modulus;

    private BigInteger publicExponent;

    private BigInteger privateExponent;

    private String password;

    public RSAModel() {

    }

    public BigInteger getModulus() {
        return modulus;
    }

    public void setModulus(BigInteger modulus) {
        this.modulus = modulus;
    }

    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public void setPublicExponent(BigInteger publicExponent) {
        this.publicExponent = publicExponent;
    }

    public BigInteger getPrivateExponent() {
        return privateExponent;
    }

    public void setPrivateExponent(BigInteger privateExponent) {
        this.privateExponent = privateExponent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
