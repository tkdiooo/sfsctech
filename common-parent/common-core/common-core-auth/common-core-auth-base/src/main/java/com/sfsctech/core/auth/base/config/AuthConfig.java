package com.sfsctech.core.auth.base.config;

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
@Import(WebsiteProperties.class)
public class AuthConfig {

    @Autowired
    private WebsiteProperties websiteProperties;

    private static Set<String> SESSION_EXCLUDES_PATTERNS;

    public Set<String> getSessionExcludePath() {
        if (null != SESSION_EXCLUDES_PATTERNS) {
            return SESSION_EXCLUDES_PATTERNS;
        }
        SESSION_EXCLUDES_PATTERNS = new HashSet<>(FilterHandler.FILTER_EXCLUDES_PATTERNS);
        if (ListUtil.isNotEmpty(websiteProperties.getSession().getExcludePath())) {
            SESSION_EXCLUDES_PATTERNS.addAll(websiteProperties.getSession().getExcludePath());
        }
        return SESSION_EXCLUDES_PATTERNS;
    }

    public String getWelcomeFile() {
        return websiteProperties.getSupport().getWelcomeFile();
    }

}
