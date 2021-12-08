package com.employeeswebapi;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.NotFoundException;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation of Employee Entity Manager.
 */

@AllArgsConstructor
@Getter
@Setter
@ToString
@ApplicationScoped
public class EmployeesManager implements PanacheRepositoryBase<Employee, Long> {

    /**
     * Retrieves all Employees persisted in the database.
     *
     * @return the List of the {@link Employee} persisted in DB.
     */
    public List<Employee> getAllEmployees() {
        return listAll();
    }

    /**
     * Retrieves the Employee from the DB, given his ID.
     *
     * @param employeeID the employee ID.
     *
     * @return the {@link Employee} that matches with the given employeeID.
     *
     * @throws NotFoundException when the employee not found in DB.
     */
    public Employee getEmployeeByID(@NotBlank final Long employeeID) {
        Optional<Employee> employee = Optional.ofNullable(findById(employeeID));
        if (!employee.isPresent()) {
            throw new NotFoundException(("Employee does not exist"));
        }
        return employee.get();
    }

    /**
     * Inserts a new employee to the DB.
     *
     * @param employee the employee ID.
     *
     */
    @Transactional
    public boolean addNewEmployee(@NotBlank final Employee employee) {
        if(Optional.ofNullable(findById(employee.getEmployeeID())).isPresent()){
            return false;
        }
        persist(employee);
        return true;
    }

    /**
     * Removes an employee from the DB, given his ID.
     *
     * @param employeeID the employee ID.
     *
     * @return true if the given employeeID exists in DB, false otherwise.
     *
     * @throws NotFoundException when the employeeID not found in DB.
     */
    @Transactional
    public boolean deleteAnEmployee(@NotBlank final Long employeeID) {
        Optional<Employee> employee = Optional.ofNullable(findById(employeeID));
        if (!employee.isPresent()) {
            throw new NotFoundException(("Employee does not exist"));
        }
        return deleteById(employeeID);
    }

    /**
     * Updates an employee info, given his ID.
     *
     * @param employeeID the employee ID.
     *
     * @return true if the given employeeID exists in and if his info was updated, false otherwise.
     *
     * @throws NotFoundException when the employeeID not found in DB.
     */
    @Transactional
    public boolean updateEmployeeInfo(@NotBlank final Long employeeID, @NotBlank final Employee employeeUpdated) {
        Optional<Employee> employee = Optional.ofNullable(findById(employeeID));
        if (!employee.isPresent()) {
            throw new NotFoundException(("Employee does not exist"));
        }
        return update("firstName = ?1, lastName = ?2, baseSalary = ?3 WHERE employeeID = ?4", employeeUpdated.getFirstName(),
            employeeUpdated.getLastName(), employeeUpdated.getBaseSalary(), employeeID) > 0;
    }

}