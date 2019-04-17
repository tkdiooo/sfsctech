package com.sfsctech.core.auth.sso.server.jwt;

import com.sfsctech.core.base.domain.dto.BaseDto;
import lombok.*;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenStore extends BaseDto {

    private static final long serialVersionUID = 5838366730081150504L;

    private String username;

    private Date beginTime;

    private Date endTime;

    private Date logoutTime;

}
