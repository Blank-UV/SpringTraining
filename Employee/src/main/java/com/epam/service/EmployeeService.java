package com.epam.service;

import com.epam.dto.EmployeeDto;
import com.epam.model.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployee();

    public Employee getEmployeeByName(String firstname);

    public boolean addEmployee(EmployeeDto employeeDto);

    public boolean removeEmployee(String firstname);
}
