package com.sfsctech.core.auth.base.sso.constants;

import com.sfsctech.core.auth.base.constants.SessionConstants;

/**
 * Class SSOConstants
 *
 * @author 张麒 2016/5/19.
 * @version Description:
 */
public class SSOConstants extends SessionConstants {


    public static final String LOGIN_ACCOUNT = "account";
    public static final String LOGIN_REMEMBER = "remember";

    // 登录
    public static final String LOGING_URL = "loginUrl";
    // 注册
    public static final String REGISTER_URL = "registerUrl";
    // 找回密码
    public static final String FORGET_URL = "forgetUrl";
    // 登出
    public static final String LOGOUT_URL = "logoutUrl";

    public static final String COOKIE_REMEMBER_LOGIN_ACCOUNT = "re_lo_ac";

    public static final String COOKIE_ACCESS_TOKEN = "uams_ac_tok_na";
    public static final String HEADER_ACCESS_TOKEN = "Authorization";
    // SSO验证参数名称
    public static final String UAMS_CHECK_PARAM_NAME = "uams_check_param";


    public static final String JWT_CLAIMS_SCOPES = "scopes";
    public static final String JWT_USER_AUTH_INFO = "jwt_user_auth_info";
    public static final String JWT_PERMIT_ATTRIBUTE = "jwt_permit_attribute";

    public static final String ACCESS_TOKEN_CACHE_IDENTIFY = "access_token_id";
    public static final String REFRESH_TOKEN_CACHE_IDENTIFY = "refresh_token_id";

    public enum Scopes {
        REFRESH_TOKEN;

        public String authority() {
            return "ROLE_" + this.name();
        }
    }
}
