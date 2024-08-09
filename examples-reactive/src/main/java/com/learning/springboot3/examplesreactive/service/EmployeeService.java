package com.learning.springboot3.examplesreactive.service;

import com.learning.springboot3.examplesreactive.dto.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EmployeeService {

    Flux<Employee> getAll();

    Mono<Employee> save(Employee employee);

}
