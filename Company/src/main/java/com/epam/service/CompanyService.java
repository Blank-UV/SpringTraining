package com.epam.service;

import java.util.List;

import com.epam.dto.CompanyDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import com.epam.model.Company;

public interface CompanyService {

	public List<Company> getAllCompany();
	public Company getCompanyByName(String name) throws ParameterNotCorrectException, NotFoundException;
	public boolean addCompany(CompanyDto companyDto) throws DuplicateDataException;
	public boolean removeCompany(String name) throws NotFoundException;

}
