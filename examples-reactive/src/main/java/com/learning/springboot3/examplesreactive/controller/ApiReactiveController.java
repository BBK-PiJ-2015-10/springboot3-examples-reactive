package com.learning.springboot3.examplesreactive.controller;


import com.learning.springboot3.examplesreactive.dto.Employee;
import com.learning.springboot3.examplesreactive.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ApiReactiveController {

    private final EmployeeService employeeService;

    public ApiReactiveController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/api/employees")
    Flux<Employee> employees()  {
            return Flux.just(
                    new Employee("alice","Software-DA"),
                    new Employee("Alex","Software-Dev")
            );
    }

}
