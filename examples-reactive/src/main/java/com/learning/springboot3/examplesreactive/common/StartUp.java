package com.learning.springboot3.examplesreactive.common;

import com.learning.springboot3.examplesreactive.entity.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@Configuration
public class StartUp {

    private final Integer threadPoolSize = 5;
    private final Integer taskQueueSize = 5;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Bean
    CommandLineRunner initDatabase(R2dbcEntityTemplate template) {
        return args -> {
            template
                    .getDatabaseClient()
                    .sql("CREATE TABLE EMPLOYEE (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(255), role VARCHAR(255))")
                    .fetch()
                    .rowsUpdated()
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();

            template
                    .insert(EmployeeEntity.class)
                    .using(new EmployeeEntity("Alex", "Panda"))
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();

            template
                    .insert(EmployeeEntity.class)
                    .using(new EmployeeEntity("Alejandro London", "Giraffa"))
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();


            template
                    .insert(EmployeeEntity.class)
                    .using(new EmployeeEntity("Alexander Paris", "Gelato"))
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();

        };
    }

    @Bean
    public Scheduler publishEventScheduler() {
        logger.info("Creates a messagingScheduler with connectionPoolSize = {}", threadPoolSize);
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "publish-pool");
    }


}
