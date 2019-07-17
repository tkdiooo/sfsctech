package com.sfsctech.support.jwt.model;

import com.sfsctech.core.base.domain.dto.BaseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class JwtResult
 *
 * @author 张麒 2019-7-16.
 * @version Description:
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class JwtResult extends BaseDto {

    private static final long serialVersionUID = 5431564573523091513L;

    private String accessToken;
    private String refreshJwt;

}
