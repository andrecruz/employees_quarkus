package com.employeeswebapi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employee {

    @Setter(value = AccessLevel.NONE)
    private int employeeID;
    private String firstName;
    private String lastName;
    private Double baseSalary;

}