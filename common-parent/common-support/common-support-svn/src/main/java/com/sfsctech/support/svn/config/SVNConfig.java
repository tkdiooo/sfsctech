package com.sfsctech.support.svn.config;

import com.sfsctech.support.svn.properties.SVNPropertyies;
import com.sfsctech.support.svn.util.SvnHelper;
import com.sfsctech.support.svn.util.SvnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class SVNConfig
 *
 * @author 张麒 2021-2-20.
 * @version Description:
 */
@Configuration
@Import(SVNPropertyies.class)
public class SVNConfig {

    @Autowired
    private SVNPropertyies properties;

    @Bean
    public SvnHelper svnHelper() throws Exception {
        return new SvnHelper(properties.getUrl(), properties.getAccount(), properties.getPassword());
    }

    @Bean
    public SvnUtil svnUtil(SvnHelper svnHelper) {
        return new SvnUtil(svnHelper, properties.getLocalPath());
    }
}
