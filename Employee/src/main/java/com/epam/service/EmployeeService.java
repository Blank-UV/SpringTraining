package com.epam.service;

import com.epam.dto.EmployeeDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import com.epam.model.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployee();

    public Employee getEmployeeByName(String firstname) throws ParameterNotCorrectException, NotFoundException;

    public boolean addEmployee(EmployeeDto employeeDto) throws DuplicateDataException;

    public boolean removeEmployee(String firstname) throws NotFoundException;
}
