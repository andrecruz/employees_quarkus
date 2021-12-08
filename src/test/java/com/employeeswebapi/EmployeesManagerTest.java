package com.employeeswebapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import io.quarkus.test.junit.QuarkusTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class EmployeesManagerTest {

    private static final Employee EMPLOYEE_1 = createNewEmployee(1L, "Thomas", "Baker", 2500.00);
    private static final Employee EMPLOYEE_2 = createNewEmployee(2L, "Andre", "Cruz", 3300.00);
    private static final Employee EMPLOYEE_3 = createNewEmployee(3L, "John", "Campbell", 2800.00);
    private static final Employee EMPLOYEE_4 = createNewEmployee(1L, "John", "Campbell", 2800.00);

    @Inject
    EmployeesManager victim;

    @Transactional
    @BeforeEach
    void populateDB() {
        victim.deleteAll();
        victim.persist(EMPLOYEE_1);
        victim.persist(EMPLOYEE_2);
    }

    @Transactional
    @AfterEach
    void cleanup() {
        victim.deleteAll();
    }

    @Test
    void Should_GetEmployeeByID() {
        assertTrue(EMPLOYEE_1.equals(victim.getEmployeeByID(1L)));
    }

    @Test
    void Should_AddNewEmployeeToTheList() {
        victim.addNewEmployee(EMPLOYEE_3);

        assertTrue(victim.getAllEmployees().contains(EMPLOYEE_3));
    }

    @Test
    @SneakyThrows
    void Should_DeleteAnEmployeeFromTheList() {
        victim.deleteAnEmployee(1L);

        assertFalse(victim.getAllEmployees().contains(EMPLOYEE_1));
    }

    @Test
    @SneakyThrows
    void Should_UpdateAnEmployeeInfo() {
        victim.updateEmployeeInfo(1L, EMPLOYEE_3);

        assertTrue(victim.getEmployeeByID(1L).equals(EMPLOYEE_4));
    }


    @Test
    void Should_ThrowException_WhenEmployeeDoesNotExist() {
        final String expectedException = "Employee does not exist";

        final var exception = Assertions.assertThrows(NotFoundException.class, () -> victim.updateEmployeeInfo(4L, EMPLOYEE_4));

        assertEquals(expectedException, exception.getMessage());
    }



    private static Employee createNewEmployee(final Long employeeID, final String firstName, final String lastName,
                                              final double baseSalary) {
        return Employee.builder()
            .employeeID(employeeID)
            .firstName(firstName)
            .lastName(lastName)
            .baseSalary(baseSalary)
            .build();
    }

}