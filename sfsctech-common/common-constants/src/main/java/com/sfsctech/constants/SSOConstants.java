package com.sfsctech.constants;

/**
 * Class SSOConstants
 *
 * @author 张麒 2016/5/19.
 * @version Description:
 */
public class SSOConstants {

    public static final String UTF_8 = "UTF-8";

    public static final String PARAM_FROM_URL = "from_url";

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

    public static final String COOKIE_REMEMBER_LOGIN_ACCOUNT = "sfsc_re_lo_ac";

    public static final String COOKIE_TOKEN_NAME = "uams_tok_na";
    public static final String COOKIE_SALT_CACHE_KEY_NAME = "uams_tok_cac_na";
    // Session参数名称
    public static final String CONST_UAMS_ASSERTION = "const_uams_assertion";
    // session 过期
    public static final String SESSION_TIME_OUT = "timeout";

    public static final String JWT_USER_AUTH_INFO = "jwt_user_auth_info";
    public static final String JWT_SESSION_ATTRIBUTE = "jwt_session_attribute";

    // 校验协议
    public enum AuthProtocol {
        Http, Rpc
    }

    // 校验方式
    public enum AuthWay {
        Simple, Complex
    }

}
