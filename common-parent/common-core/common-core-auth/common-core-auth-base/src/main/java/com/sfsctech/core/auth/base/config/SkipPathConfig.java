package com.sfsctech.core.auth.base.config;

import com.sfsctech.core.auth.base.properties.AuthProperties;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.web.properties.WebsiteProperties;
import com.sfsctech.support.common.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Set;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@Import({WebsiteProperties.class, AuthProperties.class})
public class SkipPathConfig {

    @Autowired
    private WebsiteProperties website;

    @Autowired
    private AuthProperties auth;

    private static Set<String> SESSION_EXCLUDES_PATTERNS;

    public Set<String> getExcludePath() {
        if (null != SESSION_EXCLUDES_PATTERNS) {
            return SESSION_EXCLUDES_PATTERNS;
        }
        // 默认静态资源路径
        SESSION_EXCLUDES_PATTERNS = new HashSet<>(FilterHandler.FILTER_EXCLUDES_PATTERNS);
        // 自定义排除路径
        if (ListUtil.isNotEmpty(auth.getExcludePath())) {
            SESSION_EXCLUDES_PATTERNS.addAll(auth.getExcludePath());
        }
        // 默认登录页面
        SESSION_EXCLUDES_PATTERNS.add(auth.getLogin().getUrl());
        return SESSION_EXCLUDES_PATTERNS;
    }

    public String getWelcomeFile() {
        return website.getSupport().getWelcomeFile();
    }

    public AuthProperties auth() {
        return this.auth;
    }

}
