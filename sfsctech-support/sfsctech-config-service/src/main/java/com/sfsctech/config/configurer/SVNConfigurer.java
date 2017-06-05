package com.sfsctech.config.configurer;

import com.sfsctech.config.util.SVNUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 * Class SVNConfigurer
 *
 * @author 张麒 2017/6/5.
 * @version Description:
 */
@Configuration
public class SVNConfigurer {

    @Bean
    @ConfigurationProperties(prefix = "spring.cloud.config.server.svn")
    public SVNClientManager svnClientManager() throws Exception {
        SVNUtil svn = new SVNUtil();
        return svn.initSVNClientManager();
    }
}
