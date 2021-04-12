package com.sfsctech.config.configurer;

import com.sfsctech.config.util.SvnHelper;
import com.sfsctech.config.util.SvnUtil;
import com.sfsctech.core.web.properties.WebsiteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * Class SVNConfigurer
 *
 * @author 张麒 2017/6/5.
 * @version Description:
 */
@Configuration
@Import(WebsiteProperties.class)
public class SVNConfigurer {

    @Autowired
    private WebsiteProperties properties;

    @Bean
    public SvnHelper svnHelper() throws Exception {
        Map<String, String> map = properties.getSupport().getCustomConfig();
        return new SvnHelper(map.get("svn-uri"), map.get("svn-username"), map.get("svn-password"));
    }

    @Bean
    public SvnUtil svnUtil(SvnHelper svnHelper) {
        return new SvnUtil(svnHelper, properties.getSupport().getCustomConfig().get("svn-path"));
    }
}
