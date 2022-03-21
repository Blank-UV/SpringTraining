package com.epam.Controller;

import com.epam.dto.CompanyDto;
import com.epam.model.Company;
import com.epam.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @MockBean
    CompanyService companyService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private CompanyDto companyDto;
    private Company company;
    ArrayList<Company> companyList;

    private static final String URL = "/company";
    private static final String NAME = "Epam";
    private static final String DISTRICT = "District";
    private static final int PIN = 12345;
    private static final String STATE = "State";

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @BeforeEach
    void setup() {
        companyDto = new CompanyDto();
        companyDto.setName(NAME);
        companyDto.setDistrict("District");
        companyDto.setPin(12345);
        companyDto.setState("State");

        company = new Company();
        company.setName(NAME);
        company.setDistrict("District");
        company.setPin(12345);
        company.setState("State");

        companyList = new ArrayList<>();
        companyList.add(company);
    }

    @Test
    void getAllCompanyTest() throws Exception {

        when(companyService.getAllCompany()).thenReturn(companyList);

        mockMvc.perform(get(URL + "/companylist").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].district").value(DISTRICT))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pin").value(PIN))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state").value(STATE));
        verify(companyService, times(1)).getAllCompany();
    }

    @Test
    void getByCompanyIdTest() throws Exception {
        when(companyService.getCompanyByName(NAME)).thenReturn(company);
        mockMvc.perform(get(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("district").value(DISTRICT))
                .andExpect(MockMvcResultMatchers.jsonPath("pin").value(PIN))
                .andExpect(MockMvcResultMatchers.jsonPath("state").value(STATE));

        verify(companyService, times(1)).getCompanyByName(NAME);

    }

    @Test
    void addCompanyTest() throws Exception {
        when(companyService.addCompany(any())).thenReturn(true);

        mockMvc.perform(post(URL + "/addCompany").header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Company added"));

        verify(companyService, times(1)).addCompany(any());
    }

    @Test
    void removeCompanyTest() throws Exception {
        when(companyService.removeCompany(NAME)).thenReturn(true);
        mockMvc.perform(delete(URL + "/" + NAME).header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(companyService, times(1)).removeCompany(NAME);

    }

}