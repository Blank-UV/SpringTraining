package com.epam.service.impl;

import com.epam.dto.EmployeeDto;
import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import com.epam.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    ModelMapper mapper;

    public List<Employee> getAllEmployee() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee getEmployeeByName(String firstname) {
        return employeeRepo.getById(firstname);
    }

    @Override
    public boolean addEmployee(EmployeeDto employeeDto) {
        Employee employee = mapper.map(employeeDto, Employee.class);
        employeeRepo.save(employee);
        return true;
    }

    @Override
    public boolean removeEmployee(String firstname) {
        Employee employee = employeeRepo.getById(firstname);
        employeeRepo.delete(employee);
        return true;
    }

}
