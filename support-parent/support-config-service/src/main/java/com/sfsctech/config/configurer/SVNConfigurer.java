package com.sfsctech.config.configurer;

import com.sfsctech.config.util.SvnHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class SVNConfigurer
 *
 * @author 张麒 2017/6/5.
 * @version Description:
 */
@Configuration
public class SVNConfigurer {

    @Value("${spring.cloud.config.server.svn.uri}")
    private String uri;
    @Value("${spring.cloud.config.server.svn.username}")
    private String username;
    @Value("${spring.cloud.config.server.svn.password}")
    private String password;

    @Bean
    public SvnHelper svnHelper() throws Exception {
        return new SvnHelper(uri, username, password);
    }
}
