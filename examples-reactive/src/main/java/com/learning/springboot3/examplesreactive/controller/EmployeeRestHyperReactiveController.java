package com.learning.springboot3.examplesreactive.controller;

import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.service.EmployeeService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@EnableHypermediaSupport(type = HAL)
public class EmployeeRestHyperReactiveController {


    private EmployeeService employeeService;

    public EmployeeRestHyperReactiveController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hypermedia/employees/{name}")
    Mono<EntityModel<EmployeeDto>> employee(@PathVariable String name) {

        Mono<Link> selfLink =
                linkTo(
                        methodOn(EmployeeRestHyperReactiveController.class)
                                .employee(name)
                ).withSelfRel()
                        .toMono();

        Mono<Link> aggregateRoot =
                linkTo(
                        methodOn(EmployeeRestHyperReactiveController.class)
                                .employees()
                ).withRel(LinkRelation.of("employees"))
                        .toMono();


        Mono<Tuple2<Link, Link>> links = Mono.zip(selfLink, aggregateRoot);

        Mono<EntityModel<EmployeeDto>> response = links
                .map(objects ->
                        employeeService.getByName(name)
                                .map(e -> EntityModel.of(e, objects.getT1(), objects.getT2()))
                ).flatMap(e -> e);

        return response;
    }


    @GetMapping("/hypermedia/employees")
    Mono<CollectionModel<EntityModel<EmployeeDto>>> employees() {

        Mono<Link> selfLink = linkTo(
                methodOn(EmployeeRestHyperReactiveController.class)
                        .employees()
        ).withSelfRel().toMono();

        Mono<CollectionModel<EntityModel<EmployeeDto>>> response = selfLink
                .flatMap(self -> employeeService.getAll().map(e -> e.name())
                        .flatMap(key -> employee(key))
                        .collectList()
                        .map(entityModels -> CollectionModel.of(entityModels, self)));

        return response;
    }

}
