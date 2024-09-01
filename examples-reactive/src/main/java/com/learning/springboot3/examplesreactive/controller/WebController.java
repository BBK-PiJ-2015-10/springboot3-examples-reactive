package com.learning.springboot3.examplesreactive.controller;


import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class WebController {

    private EmployeeService employeeService;

    public WebController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public Mono<Rendering> index() {
        return employeeService.getAll().collectList()
                .map(employees ->
                        Rendering.view("index")
                                .modelAttribute("employees", employees)
                                .modelAttribute("newEmployee",new EmployeeDto(null,"",""))
                                .build()
                );

    }

    @PostMapping("/new-employee")
    Mono<String> newEmployee(@ModelAttribute Mono<EmployeeDto> newEmployee) {
        return newEmployee
                .flatMap(e ->
                        employeeService.save(e))
                .map(e -> "redirect:/");
    }

}
