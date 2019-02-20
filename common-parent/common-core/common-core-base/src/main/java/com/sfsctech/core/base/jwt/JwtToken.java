package com.sfsctech.core.base.jwt;


import com.sfsctech.core.base.domain.dto.BaseDto;

/**
 * Class JwtToken
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class JwtToken extends BaseDto {

    private static final long serialVersionUID = 2763237712905519322L;

    // jwt信息（根据盐值加密）
    private String jwt;
    // jwt凭证
    private String certificate;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }


    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
