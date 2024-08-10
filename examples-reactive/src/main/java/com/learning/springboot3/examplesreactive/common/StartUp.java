package com.learning.springboot3.examplesreactive.common;

import com.learning.springboot3.examplesreactive.entity.EmployeeEntity;
import reactor.test.StepVerifier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@Configuration
public class StartUp {

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
                    .using(new EmployeeEntity("Alex Berlin", "Panda"))
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


}
