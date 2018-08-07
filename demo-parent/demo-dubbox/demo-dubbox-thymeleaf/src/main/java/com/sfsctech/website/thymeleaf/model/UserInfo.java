package com.sfsctech.website.thymeleaf.model;


import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.security.annotation.Encrypt;
import com.sfsctech.core.spring.constants.VerifyConstants;
import com.sfsctech.support.common.security.EncrypterTool;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Class UserInfo
 *
 * @author 张麒 2017/7/26.
 * @version Description:
 */
public class UserInfo extends BaseDto {

    private static final long serialVersionUID = 4750509479154620098L;
    @NotBlank
    @Length(min = 5, max = 20, message = VerifyConstants.Length)
    private String userName;
    @Encrypt(EncrypterTool.Security.Md5)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
