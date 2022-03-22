package com.epam.controller;

import com.epam.dto.EmployeeDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import com.epam.model.Employee;
import com.epam.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private EmployeeDto employeeDto;
    private Employee employee;
    ArrayList<Employee> employeeList;

    private static final String URL = "/employee";
    private static final String NAME = "Yuvaraj";
    private static final String COMPANY = "Epam";
    private static final int AGE = 22;
    private static final String SECONDNAME = "Pradhan";

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @BeforeEach
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

        when(employeeService.getAllEmployee()).thenReturn(employeeList);

        mockMvc.perform(get(URL + "/employeelist").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(AGE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].secondName").value(SECONDNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company").value(COMPANY));
        verify(employeeService, times(1)).getAllEmployee();
    }

    @Test
    void getByEmployeeIdTest() throws Exception {
        when(employeeService.getEmployeeByName(NAME)).thenReturn(employee);
        mockMvc.perform(get(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("age").value(AGE))
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("secondName").value(SECONDNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("company").value(COMPANY));
        verify(employeeService, times(1)).getEmployeeByName(NAME);

    }

    @Test
    void getByCompanyIdTest_ParameterNotCorrectException() throws Exception {
        when(employeeService.getEmployeeByName(NAME)).thenThrow(new ParameterNotCorrectException("ParameterNotCorrectException"));
        mockMvc.perform(get(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_ACCEPTABLE.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("errormessage").value("ParameterNotCorrectException"));
        verify(employeeService, times(1)).getEmployeeByName(NAME);

    }

    @Test
    void getByCompanyIdTest_NotFoundException() throws Exception {
        when(employeeService.getEmployeeByName(NAME)).thenThrow(new NotFoundException("NotFoundException"));
        mockMvc.perform(get(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("errormessage").value("NotFoundException"));
        verify(employeeService, times(1)).getEmployeeByName(NAME);

    }
    @Test
    void addEmployeeTest() throws Exception {
        when(employeeService.addEmployee(any())).thenReturn(true);

        mockMvc.perform(post(URL + "/addEmployee").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employeeDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Employee added"));

        verify(employeeService, times(1)).addEmployee(any());
    }

    @Test
    void addCompanyTest_DuplicateDataException() throws Exception {
        when(employeeService.addEmployee(any())).thenThrow(new DuplicateDataException("DuplicateDataException"));
        mockMvc.perform(post(URL + "/addEmployee").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employeeDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.jsonPath("errormessage").value("DuplicateDataException"));
        verify(employeeService, times(1)).addEmployee(any());

    }
    @Test
    void removeEmployeeTest() throws Exception {
        when(employeeService.removeEmployee(NAME)).thenReturn(true);
        mockMvc.perform(delete(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(employeeService, times(1)).removeEmployee(NAME);

    }

    @Test
    void removeCompanyTest_NotFoundException() throws Exception {
        when(employeeService.removeEmployee(NAME)).thenThrow(new NotFoundException("NotFoundException"));
        mockMvc.perform(delete(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("errormessage").value("NotFoundException"));
        verify(employeeService, times(1)).removeEmployee(NAME);

    }

}
