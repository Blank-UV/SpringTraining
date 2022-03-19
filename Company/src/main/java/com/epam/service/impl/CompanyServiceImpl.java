package com.epam.service.impl;

import java.util.List;

import com.epam.dto.CompanyDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.model.Company;
import com.epam.repository.CompanyRepository;
import com.epam.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Company> getAllCompany() {
		return (List<Company>) companyRepo.findAll();
	}

	@Override
	public Company getCompanyByName(String name) {
		return companyRepo.findByName(name);
	}

	@Override
	public boolean addCompany(CompanyDto companyDto) {
		Company company = modelMapper.map(companyDto,Company.class);
		companyRepo.save(company);
		return true;
	}

	@Override
	public boolean removeCompany(String name) {
		Company company = companyRepo.findByName(name);
		companyRepo.delete(company);
		return true;
	}
}