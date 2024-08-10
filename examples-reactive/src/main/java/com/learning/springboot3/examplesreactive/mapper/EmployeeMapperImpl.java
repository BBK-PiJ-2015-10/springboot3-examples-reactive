package com.learning.springboot3.examplesreactive.mapper;

import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.entity.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDto toDto(EmployeeEntity employeeEntity) {
        return new EmployeeDto(employeeEntity.getName(), employeeEntity.getRole());
    }

    @Override
    public EmployeeEntity toEntity(EmployeeDto employeeDto) {
        return new EmployeeEntity(employeeDto.name(), employeeDto.department());
    }
}
