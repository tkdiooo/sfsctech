package com.sfsctech.website.jsp;

import com.sfsctech.common.spring.util.JavaConfigUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;


@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.website.jsp", "com.sfsctech.common"})
public class JspWebRunner extends SpringBootServletInitializer {

    @Resource
    private JavaConfigUtil springUtil;

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet servlet) {
        ServletRegistrationBean registration = springUtil.getServletRegistrationBean(servlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.html");
        registration.addUrlMappings("*.ajax");
        return registration;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        TomcatContextCustomizer contextCustomizer = context -> context.addWelcomeFile("/index.html");
        factory.addContextCustomizers(contextCustomizer);
        return factory;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JspWebRunner.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JspWebRunner.class, args);
    }

}
