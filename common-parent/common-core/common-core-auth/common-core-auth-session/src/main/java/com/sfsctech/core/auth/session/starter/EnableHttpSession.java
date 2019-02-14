package com.sfsctech.core.auth.session.starter;

import com.sfsctech.core.auth.session.config.SessionConfig;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.lang.annotation.*;

/**
 * Class EnableHttpSession
 *
 * @author 张麒 2019-2-14.
 * @version Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableRedisHttpSession
@Import(SessionConfig.class)
public @interface EnableHttpSession {
}
