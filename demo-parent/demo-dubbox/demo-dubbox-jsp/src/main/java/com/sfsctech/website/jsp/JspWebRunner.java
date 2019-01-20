package com.sfsctech.website.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.framework", "com.sfsctech.website.jsp"})
public class JspWebRunner extends SpringBootServletInitializer {

//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet servlet) {
//        ServletRegistrationBean registration = springUtil.getServletRegistrationBean(servlet);
//        registration.getUrlMappings().clear();
//        registration.addUrlMappings("*.html");
//        registration.addUrlMappings("*.ajax");
//        return registration;
//    }
//
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//        TomcatContextCustomizer contextCustomizer = context -> context.addWelcomeFile(appConfig.getWebsiteProperties().getSupport().getWelcomeFile());
//        factory.addContextCustomizers(contextCustomizer);
//        return factory;
//    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JspWebRunner.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(JspWebRunner.class, args);
    }

}
