package com.learning.springboot3.examplesreactive.controller;


import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeRestReactiveController {

    private final EmployeeService employeeService;

    public EmployeeRestReactiveController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/api/employees")
    Flux<EmployeeDto> employees() {
//        return Flux.just(
//                new EmployeeDto("alice", "Software-DA"),
//                new EmployeeDto("Alex", "Software-Dev")
//        );
        return employeeService.getAll();
    }

    @PostMapping("/api/employees")
    Mono<EmployeeDto> createEmployee(@RequestBody Mono<EmployeeDto> employee) {

        System.out.println("CULONS");


        return employee.flatMap(e ->
                employeeService.save(e));
    }

}
