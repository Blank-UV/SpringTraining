package com.epam.service;

import com.epam.dto.CompanyDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import com.epam.model.Company;
import com.epam.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyServiceTest {

    @MockBean
    CompanyRepository companyRepo;

    @Autowired
    CompanyService companyService;

    @MockBean
    ModelMapper modelMapper;

    private CompanyDto companyDto;
    private Company company;
    ArrayList<Company> companyList;

    private static final String NAME = "Epam";
    private static final String DISTRICT = "District";
    private static final int PIN = 12345;
    private static final String STATE = "State";


    @BeforeAll
    private void setup() {
        companyDto = new CompanyDto();
        companyDto.setName(NAME);
        companyDto.setDistrict(DISTRICT);
        companyDto.setPin(PIN);
        companyDto.setState(STATE);

        company = new Company();
        company.setName(NAME);
        company.setDistrict(DISTRICT);
        company.setPin(PIN);
        company.setState(STATE);

        companyList = new ArrayList<>();
        companyList.add(company);
    }
    @Test
    void getAllCompanyTest() {
        when(companyRepo.findAll()).thenReturn(companyList);
        List<Company> actualOutput = companyService.getAllCompany();
        assertEquals(actualOutput.get(0).getDistrict(),company.getDistrict());
        assertEquals(actualOutput.get(0).getName(),company.getName());
        assertEquals(actualOutput.get(0).getPin(),company.getPin());
        assertEquals(actualOutput.get(0).getState(),company.getState());

        verify(companyRepo,times(1)).findAll();
    }

    @Test
    public void getCompanyByNameTest() throws ParameterNotCorrectException, NotFoundException {

        when(companyRepo.existsByName(NAME)).thenReturn(true);
        when(companyRepo.findByName(NAME)).thenReturn(company);
        Company actualOutput = companyService.getCompanyByName(NAME);
        assertEquals(actualOutput.getDistrict(),company.getDistrict());
        assertEquals(actualOutput.getName(),company.getName());
        assertEquals(actualOutput.getPin(),company.getPin());
        assertEquals(actualOutput.getState(),company.getState());

        verify(companyRepo,times(1)).findByName(NAME);
    }

    @Test
    public void getCompanyByNameTestForNotFoundException(){
        when(companyRepo.existsByName(NAME)).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class,()->{
            companyService.getCompanyByName(NAME);
        });
        assertEquals("Company with name " + NAME + " is not found in database",ex.getMessage());
    }

    @Test
    public void getCompanyByNameTestForParameterNotCorrectException(){
        String name = " ";
        when(companyRepo.existsByName(name)).thenReturn(false);
        ParameterNotCorrectException ex = assertThrows(ParameterNotCorrectException.class,()->{
            companyService.getCompanyByName(name);
        });
        assertEquals("Name cannot be blank",ex.getMessage());
    }

    @Test
    public void addCompanyTest() throws DuplicateDataException {
        when(companyRepo.existsByName(NAME)).thenReturn(false);
        when(modelMapper.map(companyDto,Company.class)).thenReturn(company);
        when(companyRepo.save(company)).thenReturn(company);
        assertTrue(companyService.addCompany(companyDto));

        verify(companyRepo,times(1)).save(company);
        verify(modelMapper,times(1)).map(companyDto,Company.class);
    }

    @Test
    public void addCompanyTesDuplicateDataException(){
        when(companyRepo.existsByName(NAME)).thenReturn(true);
        DuplicateDataException ex = assertThrows(DuplicateDataException.class,()->{
            companyService.addCompany(companyDto);
        });
        assertEquals("Company with Name " + NAME + " is already present in the database",ex.getMessage());
    }

    @Test
    public void removeCompanyTest() throws NotFoundException {
        when(companyRepo.existsByName(NAME)).thenReturn(true);
        when(companyRepo.findByName(NAME)).thenReturn(company);
        doNothing().when(companyRepo).delete(company);
        assertTrue(companyService.removeCompany(NAME));

        verify(companyRepo,times(1)).findByName(NAME);
        verify(companyRepo,times(1)).delete(company);
    }

    @Test
    public void removeCompanyTestNotFoundException(){
        when(companyRepo.existsByName(NAME)).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class,()->{
            companyService.removeCompany(NAME);
        });
        assertEquals("Company with name " + NAME + " is not found in database",ex.getMessage());
    }
}
