package com.sfsctech.website.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Class JspWebRunner
 *
 * @author 张麒 2017/5/4.
 * @version Description:
 */
@SpringBootApplication
public class JspWebRunner {//extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(JspWebRunner.class);
//    }

    public static void main(String[] args) throws Exception {
//        new SpringApplicationBuilder(JspWebRunner.class).web(true).run(args);
        SpringApplication.run(JspWebRunner.class, args);
    }

}
