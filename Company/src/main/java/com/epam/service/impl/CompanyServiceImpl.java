package com.epam.service.impl;

import java.lang.reflect.Parameter;
import java.util.List;

import com.epam.dto.CompanyDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
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
	public Company getCompanyByName(String name) throws ParameterNotCorrectException, NotFoundException {
		if(name.isBlank())
			throw new ParameterNotCorrectException("Name cannot be blank");
		if(!companyRepo.existsByName(name))
			throw new NotFoundException("Company with name " + name + " is not found in database");

		return companyRepo.findByName(name);
	}

	@Override
	public boolean addCompany(CompanyDto companyDto) throws DuplicateDataException {
		String name = companyDto.getName();
		if(companyRepo.existsByName(name))
			throw new DuplicateDataException("Company with Name " + name + " is already present in the database");

		Company company = modelMapper.map(companyDto,Company.class);
		companyRepo.save(company);
		return true;
	}

	@Override
	public boolean removeCompany(String name) throws NotFoundException {
		if(!companyRepo.existsByName(name))
			throw new NotFoundException("Company with name " + name + " is not found in database");

		Company company = companyRepo.findByName(name);
		companyRepo.delete(company);
		return true;
	}
}