package com.learning.springboot3.examplesreactive.service;

import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.mapper.EmployeeMapper;
import com.learning.springboot3.examplesreactive.repo.EmployeeRepository;
//import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    //private Map<String, EmployeeDto> nameEmployeeMap = new HashMap<>();

    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Flux<EmployeeDto> getAll() {
        return employeeRepository
                .findAll()
                .map(ee -> employeeMapper.toDto(ee))
                ;
    }


    @Override
    public Mono<EmployeeDto> save(EmployeeDto employeeDto) {
        return employeeRepository.save(employeeMapper.toEntity(employeeDto))
                .map(ee -> employeeMapper.toDto(ee));
    }

    @Override
    public Mono<EmployeeDto> getByName(String name) {
        return employeeRepository.findByName(name)
                .map(ee -> employeeMapper.toDto(ee))
                ;
    }

    //    @PostConstruct
//    private void LoadEmployees() {
//        var employee1 = new EmployeeDto("alice", "Software-DA");
//        var employee2 = new EmployeeDto("Alex", "Software-Dev");
//        nameEmployeeMap.put(employee1.name(), employee1);
//        nameEmployeeMap.put(employee2.name(), employee2);
//    }
}
