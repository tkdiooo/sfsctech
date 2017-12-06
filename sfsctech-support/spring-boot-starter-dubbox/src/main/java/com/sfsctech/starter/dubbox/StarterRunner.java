package com.sfsctech.starter.dubbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Class StarterRunner
 *
 * @author 张麒 2017-12-5.
 * @version Description:
 */
public class StarterRunner implements CommandLineRunner {

    protected final Logger logger = LoggerFactory.getLogger(StarterRunner.class);

    private Collection<CrudRepository> repositories;

    public StarterRunner(Collection<CrudRepository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public void run(String... strings) {
        repositories.forEach(crudRepository -> logger.info(String.format("%s has %s entries",
                getRepositoryName(crudRepository.getClass()),
                crudRepository.count())));
    }

    private static String getRepositoryName(Class crudRepositoryClass) {
        for (Class repositoryInterface : crudRepositoryClass.getInterfaces()) {
            if (repositoryInterface.getName().startsWith("com.sfsctech.configurer")) {
                return repositoryInterface.getSimpleName();
            }
        }
        return "UnknownRepository";
    }
}
