package com.employeeswebapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApplicationScoped
public class EmployeesManager {

    private Set<Employee> employeeList = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public Set<Employee> addNewEmployee(Employee employee) {
        employeeList.add(employee);
        return employeeList;
    }

    public Set<Employee> deleteAnEmployee(int employeeID) {
        employeeList.removeIf(e -> e.getEmployeeID() == employeeID);
        return employeeList;
    }

    public Employee updateEmployeeInfo(int employeeID, Employee employeeUpdated) {
        final int employeeIndex = getEmployeeIndexByEmployeeID(employeeID);
        if (employeeIndex != -1) {
            Employee employee = Employee.builder()
                .employeeID(employeeID)
                .firstName(employeeUpdated.getFirstName())
                .lastName(employeeUpdated.getLastName())
                .baseSalary(employeeUpdated.getBaseSalary())
                .build();
            employeeList.removeIf(e -> e.getEmployeeID() == employeeID);
            employeeList.add(employee);
            return employee;
        } else {
            throw new RuntimeException("Employee does not exist");
        }
    }

    public Employee getEmployeeByID (int employeeID){
        return employeeList.stream().filter(employee -> employee.getEmployeeID() == employeeID).collect(Collectors.toList()).get(0);
    }

    public int getEmployeeIndexByEmployeeID(int employeeID) {
        int index = -1;
        List<Employee> employees = new ArrayList<>(employeeList);

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeID() == employeeID) {
                index = i;
            }
        }
        return index;
    }

}