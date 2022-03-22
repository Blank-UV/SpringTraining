package com.epam.Service;

import com.epam.dto.EmployeeDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import com.epam.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    EmployeeRepository employeeRepo;

    private EmployeeDto employeeDto;
    private Employee employee;
    ArrayList<Employee> employeeList;

    private static final String URL = "/employee";
    private static final String NAME = "Yuvaraj";
    private static final String COMPANY = "Epam";
    private static final int AGE = 22;
    private static final String SECONDNAME = "Pradhan";

    @BeforeAll
    void setup() {
        employeeDto = new EmployeeDto();
        employeeDto.setAge(22);
        employeeDto.setCompany(COMPANY);
        employeeDto.setFirstname(NAME);
        employeeDto.setSecondName(SECONDNAME);

        employee = new Employee();
        employee.setAge(22);
        employee.setCompany(COMPANY);
        employee.setFirstname(NAME);
        employee.setSecondName(SECONDNAME);
        employeeList = new ArrayList<>();
        employeeList.add(employee);
    }

    @Test
    void getAllEmployeeTest() throws Exception {
        when(employeeRepo.findAll()).thenReturn(employeeList);
        List<Employee> actualOutput = employeeService.getAllEmployee();
        assertEquals(actualOutput.get(0).getAge(),employee.getAge());
        assertEquals(actualOutput.get(0).getCompany(),employee.getCompany());
        assertEquals(actualOutput.get(0).getFirstname(),employee.getFirstname());
        assertEquals(actualOutput.get(0).getSecondName(),employee.getSecondName());

        verify(employeeRepo,times(1)).findAll();
    }

    @Test
    void getEmployeeByNameTest() throws Exception {
        when(employeeRepo.existsById(NAME)).thenReturn(true);
        when(employeeRepo.getById(NAME)).thenReturn(employee);
        Employee actualOutput = employeeService.getEmployeeByName(NAME);
        assertEquals(actualOutput.getFirstname(),employee.getFirstname());
        assertEquals(actualOutput.getSecondName(),employee.getSecondName());
        assertEquals(actualOutput.getCompany(),employee.getCompany());
        assertEquals(actualOutput.getAge(),employee.getAge());

        verify(employeeRepo,times(1)).getById(NAME);
    }
    @Test
    public void getEmployeeByNameTestForNotFoundException(){
        when(employeeRepo.existsById(NAME)).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class,()->{
            employeeService.getEmployeeByName(NAME);
        });
        assertEquals("Employee with name " + NAME + " is not found in database",ex.getMessage());
    }

    @Test
    public void getEmployeeByNameTestForParameterNotCorrectException(){
        String name = " ";
        when(employeeRepo.existsById(name)).thenReturn(false);
        ParameterNotCorrectException ex = assertThrows(ParameterNotCorrectException.class,()->{
            employeeService.getEmployeeByName(name);
        });
        assertEquals("Name cannot be blank",ex.getMessage());
    }
    @Test
    void addEmployeeTest() throws Exception {
        when(modelMapper.map(employeeDto,Employee.class)).thenReturn(employee);
        when(employeeRepo.save(employee)).thenReturn(employee);
        assertTrue(employeeService.addEmployee(employeeDto));

        verify(employeeRepo,times(1)).save(employee);
        verify(modelMapper,times(1)).map(employeeDto,Employee.class);
    }

    @Test
    public void addEmployeeTesDuplicateDataException(){
        when(employeeRepo.existsById(NAME)).thenReturn(true);
        DuplicateDataException ex = assertThrows(DuplicateDataException.class,()->{
            employeeService.addEmployee(employeeDto);
        });
        assertEquals("Employee with Name " + NAME + " is already present in the database",ex.getMessage());
    }
    @Test
    void removeEmployeeTest() throws Exception {
        when(employeeRepo.existsById(NAME)).thenReturn(true);
        when(employeeRepo.getById(NAME)).thenReturn(employee);
        doNothing().when(employeeRepo).delete(employee);
        assertTrue(employeeService.removeEmployee(NAME));

        verify(employeeRepo,times(1)).getById(NAME);
        verify(employeeRepo,times(1)).delete(employee);
    }

    @Test
    public void removeCompanyTestNotFoundException(){
        when(employeeRepo.existsById(NAME)).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class,()->{
            employeeService.removeEmployee(NAME);
        });
        assertEquals("Employee with name " + NAME + " is not found in database",ex.getMessage());
    }}
