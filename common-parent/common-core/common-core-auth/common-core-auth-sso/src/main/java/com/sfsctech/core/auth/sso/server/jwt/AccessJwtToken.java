package com.sfsctech.core.auth.sso.server.jwt;


import com.sfsctech.core.auth.sso.common.jwt.JwtToken;
import com.sfsctech.core.base.domain.dto.BaseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Class JwtToken
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class AccessJwtToken extends BaseDto implements JwtToken {

    private static final long serialVersionUID = 2763237712905519322L;

    // jwt信息（根据盐值加密）
    private String jwt;

    private Date beginDate;

    private Date endDate;

}
