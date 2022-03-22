package com.epam.controller;

import com.epam.dto.EmployeeDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import com.epam.model.Employee;
import com.epam.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Api("Operation on Employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ApiOperation("Get List of all employees")
    @GetMapping("/employeelist")
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @ApiOperation("Get Employee based on the firstname")
    @GetMapping("/{firstname}")
    public Employee getByEmployeeId(@PathVariable String firstname) throws ParameterNotCorrectException, NotFoundException {
        return employeeService.getEmployeeByName(firstname);
    }

    @ApiOperation("Add Employees to the Database")
    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employeeDto) throws DuplicateDataException {

        String status = "Employee added";
        HttpStatus httpstatus = HttpStatus.CREATED;
        employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(httpstatus).body(status);
    }

    @ApiOperation("Delete employee based on firstname")
    @DeleteMapping("/{firstname}")
    public ResponseEntity<String> removeBook(@PathVariable String firstname) throws NotFoundException {

        String status = "Employee deleted";
        HttpStatus httpstatus = HttpStatus.NO_CONTENT;
        employeeService.removeEmployee(firstname);
        return ResponseEntity.status(httpstatus).body(status);
    }
}