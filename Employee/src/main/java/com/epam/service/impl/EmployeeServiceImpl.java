package com.epam.service.impl;

import com.epam.dto.EmployeeDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
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
    public Employee getEmployeeByName(String firstname) throws ParameterNotCorrectException, NotFoundException {
        if(firstname.isBlank())
            throw new ParameterNotCorrectException("Name cannot be blank");
        if(!employeeRepo.existsById(firstname))
            throw new NotFoundException("Employee with name " + firstname + " is not found in database");

        return employeeRepo.getById(firstname);
    }

    @Override
    public boolean addEmployee(EmployeeDto employeeDto) throws DuplicateDataException {
        String firstname = employeeDto.getFirstname();
        if(employeeRepo.existsById(firstname))
            throw new DuplicateDataException("Employee with Name " + firstname + " is already present in the database");

        Employee employee = mapper.map(employeeDto, Employee.class);
        employeeRepo.save(employee);
        return true;
    }

    @Override
    public boolean removeEmployee(String firstname) throws NotFoundException {
        if(!employeeRepo.existsById(firstname))
            throw new NotFoundException("Employee with name " + firstname + " is not found in database");

        Employee employee = employeeRepo.getById(firstname);
        employeeRepo.delete(employee);
        return true;
    }

}
