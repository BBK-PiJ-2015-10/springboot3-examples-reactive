package com.learning.springboot3.examplesreactive.repo;

import com.learning.springboot3.examplesreactive.entity.EmployeeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeEntity, Long> {

    Mono<EmployeeEntity> findByName(String name);

}
