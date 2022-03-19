package com.epam.service;

import java.util.List;

import com.epam.dto.CompanyDto;
import com.epam.model.Company;

public interface CompanyService {

	public List<Company> getAllCompany();
	public Company getCompanyByName(String name);
	public boolean addCompany(CompanyDto companyDto);
	public boolean removeCompany(String name);

}
