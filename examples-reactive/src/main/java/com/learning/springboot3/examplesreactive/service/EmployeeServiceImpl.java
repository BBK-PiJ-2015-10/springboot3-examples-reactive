package com.learning.springboot3.examplesreactive.service;

import com.learning.springboot3.examplesreactive.dto.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    private Map<String, Employee> nameEmployeeMap = new HashMap<>();

    @Override
    public Flux<Employee> getAll() {
        return Flux.fromIterable(nameEmployeeMap.values());
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return Mono.just(nameEmployeeMap.put(employee.name(), employee)
        );
    }

    @PostConstruct
    private void LoadEmployees() {
        var employee1 = new Employee("alice", "Software-DA");
        var employee2 = new Employee("Alex", "Software-Dev");
        nameEmployeeMap.put(employee1.name(), employee1);
        nameEmployeeMap.put(employee2.name(), employee2);
    }
}
