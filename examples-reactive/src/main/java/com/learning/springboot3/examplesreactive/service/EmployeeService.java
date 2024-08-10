package com.learning.springboot3.examplesreactive.service;

import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Flux<EmployeeDto> getAll();

    Mono<EmployeeDto> save(EmployeeDto employeeDto);

}
