package com.sfsctech.core.auth.sso.base.token.loader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class TokenLoader
 *
 * @author 张麒 2019-4-3.
 * @version Description:
 */
public interface TokenLoader {

    void loader(HttpServletRequest request, HttpServletResponse response, String token);

}
