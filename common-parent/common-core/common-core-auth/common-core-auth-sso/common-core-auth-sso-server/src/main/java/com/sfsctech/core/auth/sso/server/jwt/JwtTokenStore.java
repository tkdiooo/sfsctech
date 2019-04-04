package com.sfsctech.core.auth.sso.server.jwt;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

/**
 * Class JwtTokenStore
 *
 * @author 张麒 2019-4-4.
 * @version Description:
 */
@Data
@Builder
public class JwtTokenStore {

    private User user;

    private Date beginDate;

    private Date endDate;

}
