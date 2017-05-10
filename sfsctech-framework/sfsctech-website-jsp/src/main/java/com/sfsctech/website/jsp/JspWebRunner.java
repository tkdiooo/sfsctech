package com.sfsctech.website.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.website.jsp", "com.sfsctech.common"})
public class JspWebRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JspWebRunner.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JspWebRunner.class, args);
    }

}
