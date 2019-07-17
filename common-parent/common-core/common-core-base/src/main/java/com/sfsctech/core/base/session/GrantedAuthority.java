package com.sfsctech.core.base.session;

import java.io.Serializable;

/**
 * Class GrantedAuthority
 *
 * @author 张麒 2019-7-16.
 * @version Description:
 */
public interface GrantedAuthority extends Serializable {
    String getAuthority();
}