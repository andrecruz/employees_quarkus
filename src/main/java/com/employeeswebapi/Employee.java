package com.employeeswebapi;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Employee Entity */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @Setter(value = AccessLevel.NONE)
    private Long employeeID;
    private String firstName;
    private String lastName;
    private Double baseSalary;

}