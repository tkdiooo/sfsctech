package com.sfsctech.security.jwt;

import com.sfsctech.common.security.EncrypterTool.Security;

/**
 * Class header
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class Header {

    private String typ;

    private Security alg;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Security getAlg() {
        return alg;
    }

    public void setAlg(Security alg) {
        this.alg = alg;
    }
}
