package com.sfsctech.config.initialize;

import com.sfsctech.config.util.SvnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class SvnWorkspaceInitialize
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
@Component
public class SvnWorkspaceInitialize implements CommandLineRunner {

    @Autowired
    private SvnUtil svnUtil;

    /**
     * checkout workspace logback.xml
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        svnUtil.update(svnUtil.logbackLabel);
    }
}
