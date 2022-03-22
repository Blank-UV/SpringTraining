package com.epam.Controller;

import com.epam.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyEmployeeControllerTest {

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${employee.url}")
    private String url;

    private static final String URL = "/company";
    private EmployeeDto employeeDto;
    ArrayList<EmployeeDto> employeeList;
    HttpEntity<String> entity;

    private static final String NAME = "Yuvaraj";
    private static final String COMPANY = "Epam";
    private static final int AGE = 22;
    private static final String SECONDNAME = "Pradhan";

    @BeforeEach
    void setup() {
        employeeDto = new EmployeeDto();
        employeeDto.setAge(22);
        employeeDto.setCompany(COMPANY);
        employeeDto.setFirstname(NAME);
        employeeDto.setSecondName(SECONDNAME);

        employeeList = new ArrayList<>();

        entity = new HttpEntity<String>(createHeaders("admin", "password"));

    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

    @Test
    public void getAllEmployeeTest() throws Exception {

        ResponseEntity<String> actualResult = ResponseEntity.status(HttpStatus.OK).body("TestResult");
        when(restTemplate.exchange(url + "/employeelist", HttpMethod.GET, entity, String.class)).thenReturn(actualResult);

        mockMvc.perform(get(URL + "/employeelist").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getEmployeeByNameTest() throws Exception {

        ResponseEntity<String> actualResult = ResponseEntity.status(HttpStatus.OK).body("TestResult");
        when(restTemplate.exchange(url + "/" + NAME, HttpMethod.GET, entity, String.class)).thenReturn(actualResult);

        mockMvc.perform(get(URL + "/employee/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addEmployeeTest() throws Exception {
        ResponseEntity<String> actualResult = ResponseEntity.status(HttpStatus.OK).body("TestResult");
        HttpEntity<EmployeeDto> entity1 = new HttpEntity<>(employeeDto, createHeaders("admin", "password"));
        when(restTemplate.exchange(url + "/addEmployee", HttpMethod.POST, entity1, String.class)).thenReturn(actualResult);

        mockMvc.perform(post(URL + "/employee/addEmployee").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employeeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void removeBookTest() throws Exception {

        ResponseEntity<String> actualResult = ResponseEntity.status(HttpStatus.NO_CONTENT).body("TestResult");
        when(restTemplate.exchange(url + "/" + NAME, HttpMethod.DELETE, entity, String.class)).thenReturn(actualResult);

        mockMvc.perform(delete(URL + "/employee/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}