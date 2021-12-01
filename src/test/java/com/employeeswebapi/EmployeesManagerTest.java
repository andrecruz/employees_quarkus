package com.employeeswebapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class EmployeesManagerTest {

    private final static Employee EMPLOYEE_1 = createNewEmployee(1, "Thomas", "Baker", 2500.00);
    private final static Employee EMPLOYEE_2 = createNewEmployee(2, "Andre", "Cruz", 3300.00);
    private final static Employee EMPLOYEE_3 = createNewEmployee(3, "John", "Campbell", 2800.00);
    private final static Employee EMPLOYEE_4 = createNewEmployee(4, "Andrea", "Vieira", 2800.00);

    private AutoCloseable autoCloseable;

    @InjectMocks
    EmployeesManager victim;

    @BeforeEach
    void setupEach() {
        autoCloseable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void Should_GetEmployeeByID() {
        victim.setEmployeeList(createNewEmployeeList());

        assertTrue(victim.getEmployeeByID(1) == EMPLOYEE_1);
    }

    @Test
    void Should_AddAnEmployeeToTheList() {
        victim.setEmployeeList(createNewEmployeeList());
        victim.addNewEmployee(EMPLOYEE_4);

        assertTrue(victim.getEmployeeList().contains(EMPLOYEE_4));
    }

    @Test
    void Should_DeleteAnEmployeeFromTheList() {
        victim.setEmployeeList(createNewEmployeeList());
        victim.deleteAnEmployee(1);

        assertFalse(victim.getEmployeeList().contains(EMPLOYEE_1));
    }

    @Test
    void Should_UpdateAnEmployeeInfo() {
        Employee expectedEmployee = createNewEmployee(1, "Andrea", "Vieira", 2800.00);
        victim.setEmployeeList(createNewEmployeeList());
        victim.updateEmployeeInfo(1, EMPLOYEE_4);

        List<Employee> employeeList = new ArrayList<>(victim.getEmployeeList());
        assertTrue(compareEmployeesInfo(employeeList.get(victim.getEmployeeIndexByEmployeeID(1)), expectedEmployee));
    }

    @Test
    void Should_ThrowException_WhenEmployeeDoesNotExist() {
        String expectedException = "Employee does not exist";
        victim.setEmployeeList(createNewEmployeeList());

        final var exception = Assertions.assertThrows(RuntimeException.class, () -> victim.updateEmployeeInfo(5, EMPLOYEE_4));

        assertEquals(expectedException, exception.getMessage());
    }

    private boolean compareEmployeesInfo(Employee employee1, Employee employee2) {
        return employee1.getFirstName().contentEquals(employee2.getFirstName()) && employee1.getLastName().contentEquals(employee2.getLastName())
            && employee1.getBaseSalary().equals(employee2.getBaseSalary()) && employee1.getEmployeeID() == employee2.getEmployeeID();
    }

    private static Set<Employee> createNewEmployeeList() {
        Set<Employee> dummyEmployeeList = new HashSet<>();
        dummyEmployeeList.add(EMPLOYEE_1);
        dummyEmployeeList.add(EMPLOYEE_2);
        dummyEmployeeList.add(EMPLOYEE_3);

        return dummyEmployeeList;
    }


    private static Employee createNewEmployee(final int employeeID, final String firstName, final String lastName,
                                              final double baseSalary) {
        return Employee.builder()
            .employeeID(employeeID)
            .firstName(firstName)
            .lastName(lastName)
            .baseSalary(baseSalary)
            .build();
    }

}