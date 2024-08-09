package com.learning.springboot3.examplesreactive.controller;


import com.learning.springboot3.examplesreactive.dto.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ApiReactiveController {

    @GetMapping("/api/employees")
    Flux<Employee> employees()  {
            return Flux.just(
                    new Employee("alice","Software-DA"),
                    new Employee("Alex","Software-Dev")
            );
    }

}
