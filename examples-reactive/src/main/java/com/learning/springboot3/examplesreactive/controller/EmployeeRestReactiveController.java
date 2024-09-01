package com.learning.springboot3.examplesreactive.controller;


import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.dto.Event;
import com.learning.springboot3.examplesreactive.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.cloud.stream.function.StreamBridge;
import reactor.core.scheduler.Scheduler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import static com.learning.springboot3.examplesreactive.dto.Event.Type.CREATE;


@RestController
public class EmployeeRestReactiveController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmployeeService employeeService;

    private final StreamBridge streamBridge;

    private final Scheduler publishEventScheduler;

    @Autowired
    public EmployeeRestReactiveController(EmployeeService employeeService, StreamBridge streamBridge, Scheduler publishEventScheduler) {
        this.employeeService = employeeService;
        this.streamBridge = streamBridge;
        this.publishEventScheduler = publishEventScheduler;
    }


    //public EmployeeRestReactiveController(EmployeeService employeeService) {
    //  this.employeeService = employeeService;
    //}

    @GetMapping("/api/employees")
    Flux<EmployeeDto> employees() {
//        return Flux.just(
//                new EmployeeDto("alice", "Software-DA"),
//                new EmployeeDto("Alex", "Software-Dev")
//        );
        return employeeService.getAll();
    }

    private Mono<EmployeeDto> helper(EmployeeDto employee){
        return Mono.fromCallable(() -> {
            sendMessage("products-out-0", new Event(CREATE, employee.id(),employee));
            return employee;
        }).subscribeOn(publishEventScheduler);
    }

    @PostMapping("/api/employees")
    Mono<EmployeeDto> createEmployee(@RequestBody Mono<EmployeeDto> employee) {

        return employee.flatMap(e -> helper(e));


//        return Mono.fromCallable(() -> {
//            sendMessage("recommendations-out-0", new Event(CREATE, employee, body));
//            return employee;
//        }).subscribeOn(publishEventScheduler);
//
//        System.out.println("CULONS");
//
//
//        return employee.flatMap(e ->
//                employeeService.save(e));
    }

    private void sendMessage(String bindingName, Event event) {
        logger.debug("Sending a {} message to {}", event.getEventType(), bindingName);
        Message message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .build();
        streamBridge.send(bindingName, message);
    }

}
