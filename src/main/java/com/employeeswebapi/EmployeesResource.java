package com.employeeswebapi;

import java.util.List;
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

    @Inject
    EmployeesManager employeesManager;

    @GET
    @Path("/getAllEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeesManager.getAllEmployees();
    }

    @GET
    @Path("/getEmployeeByID/{employeeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeByID(@PathParam("employeeID") Long employeeID) {
        return employeesManager.getEmployeeByID(employeeID);
    }

    @POST
    @Path("/addNewEmployee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean addNewEmployee(final Employee employee) {
        return employeesManager.addNewEmployee(employee);
    }

    @DELETE
    @Path("/deleteEmployee/{employeeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteEmployee(@PathParam("employeeID") final Long employeeID) throws Throwable {
        return employeesManager.deleteAnEmployee(employeeID);
    }

    @PUT
    @Path("/updateEmployeeInfo/{employeeID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateEmployeeInfo(@PathParam("employeeID") final Long employeeID, final Employee employee) throws Throwable {
        employeesManager.updateEmployeeInfo(employeeID, employee);
    }

}