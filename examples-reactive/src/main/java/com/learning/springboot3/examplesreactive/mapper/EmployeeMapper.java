package com.learning.springboot3.examplesreactive.mapper;

import com.learning.springboot3.examplesreactive.dto.EmployeeDto;
import com.learning.springboot3.examplesreactive.entity.EmployeeEntity;

public interface EmployeeMapper {

    EmployeeDto toDto(EmployeeEntity employeeEntity);

    EmployeeEntity toEntity(EmployeeDto employeeDto);
}
