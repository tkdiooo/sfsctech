package com.sfsctech.core.auth.sso.base.constants;

import com.sfsctech.core.auth.base.constants.SessionConstants;
import com.sfsctech.core.base.constants.LabelConstants;

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

    public static final String COOKIE_REMEMBER_LOGIN_ACCOUNT = "sfsc_re_lo_ac";

    public static final String TOKEN_PREFIX = "Bearer" + LabelConstants.PERIOD;
    public static final String TOKEN_IDENTIFY_COOKIE = "uams_ac_tok_na";
    public static final String TOKEN_IDENTIFY_HEADER = "Authorization";
    // SSO验证参数名称
    public static final String UAMS_CHECK_PARAM_NAME = "uams_check_param";


    public static final String JWT_CLAIMS_SCOPES = "scopes";
    public static final String JWT_USER_AUTH_INFO = "jwt_user_auth_info";
    public static final String JWT_PERMIT_ATTRIBUTE = "jwt_permit_attribute";
    public static final String JWT_CANCEL_MSG = "登录用户已被管理员手动注销";
    public static final String JWT_KEYS_LIST = "jwt_keys_list";

    public static final String CACHE_IDENTIFY_ACCESS_TOKEN = "access_token_id";
}
