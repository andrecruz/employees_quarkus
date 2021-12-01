package com.employeeswebapi;

import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/employees")
public class EmployeesResource {

    private final static Employee EMPLOYEE_1 = new Employee(1, "Thomas", "Baker", 2500.00);
    private final static Employee EMPLOYEE_2 = new Employee(2, "Andre", "Cruz", 3300.00);
    private final static Employee EMPLOYEE_3 = new Employee(3, "John", "Campbell", 2800.00);
    private final static Employee EMPLOYEE_4 = new Employee(4, "Andrea", "Vieira", 2800.00);

    @Inject
    EmployeesManager employeesManager;

    public Set<Employee> employeeResource() {
        employeesManager.addNewEmployee(EMPLOYEE_1);
        employeesManager.addNewEmployee(EMPLOYEE_2);
        employeesManager.addNewEmployee(EMPLOYEE_3);
        employeesManager.addNewEmployee(EMPLOYEE_4);

        return employeesManager.getEmployeeList();
    }

    @GET
    @Path("/getAllEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Employee> getAllEmployees() {
        employeeResource();
        return employeesManager.getEmployeeList().stream().collect(Collectors.toSet());
    }

    @GET
    @Path("/getEmployeeByID/{employeeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeByID(@PathParam("employeeID") int employeeID) {
        return employeesManager.getEmployeeByID(employeeID);
    }

    @POST
    @Path("/addNewEmployee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Employee> addNewEmployee(final Employee employee) {
        employeesManager.addNewEmployee(employee);
        return employeesManager.getEmployeeList();
    }

    @DELETE
    @Path("/deleteEmployee/{employeeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Employee> deleteEmployee(@PathParam("employeeID") final int employeeID) {
        employeesManager.deleteAnEmployee(employeeID);
        return employeesManager.getEmployeeList();
    }

    @PUT
    @Path("/updateEmployeeInfo/{employeeID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Employee> updateEmployeeInfo(@PathParam("employeeID") final int employeeID, final Employee employee) {
        employeesManager.updateEmployeeInfo(employeeID, employee);
        return employeesManager.getEmployeeList();
    }
}