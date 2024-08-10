package com.learning.springboot3.examplesreactive.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("EMPLOYEE")
public class EmployeeEntity {

    private @Id Long id;

    private String name;

    private String role;

    public EmployeeEntity(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
