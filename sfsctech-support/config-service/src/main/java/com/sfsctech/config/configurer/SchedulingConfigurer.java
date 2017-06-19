package com.sfsctech.config.configurer;

import com.sfsctech.config.util.SvnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Class SchedulingConfig
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
@Configuration
@EnableScheduling
public class SchedulingConfigurer {

    @Autowired
    private SvnUtil svnUtil;

    // 每天0点 checkout workspace logback.xml
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduler() {
        svnUtil.update(svnUtil.logbackLabel);
    }

}
