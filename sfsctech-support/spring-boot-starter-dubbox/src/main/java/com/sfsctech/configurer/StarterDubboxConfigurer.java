package com.sfsctech.configurer;

import com.sfsctech.starter.dubbox.StarterRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Class StarterDubboxConfigurer
 *
 * @author 张麒 2017-12-5.
 * @version Description:
 */
//@Configuration
public class StarterDubboxConfigurer {

    @Bean
    public StarterRunner starterRunner(Collection<CrudRepository> repositories) {
        return new StarterRunner(repositories);
    }
}
