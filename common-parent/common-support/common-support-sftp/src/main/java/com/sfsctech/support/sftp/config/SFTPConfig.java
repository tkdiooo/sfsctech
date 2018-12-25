package com.sfsctech.support.sftp.config;

import com.sfsctech.support.sftp.factory.SFTPConnectionFactory;
import com.sfsctech.support.sftp.properties.SFTPProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class SFTPConfig
 *
 * @author 张麒 2018-12-25.
 * @version Description:
 */
@Configuration
@Import({SFTPProperties.class})
public class SFTPConfig {

    @Autowired
    private SFTPProperties properties;

    @Bean("sftpConnectionFactory")
    public SFTPConnectionFactory SFTPConnectionFactory() {
        return new SFTPConnectionFactory(properties);
    }

}
