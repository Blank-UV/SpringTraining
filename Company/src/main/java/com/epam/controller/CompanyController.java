package com.epam.controller;

import java.util.List;
import com.epam.dto.CompanyDto;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.epam.model.Company;
import com.epam.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@GetMapping("/companylist")
	public List<Company> getAllCompany()
	{
		return companyService.getAllCompany();
	}

	@GetMapping("/{name}")
	public Company getByCompanyId(@PathVariable String name) throws ParameterNotCorrectException, NotFoundException {
		return companyService.getCompanyByName(name);
	}

	@PostMapping("/addCompany")
	public ResponseEntity<String> addCompany(@RequestBody CompanyDto companyDto) throws DuplicateDataException {

		String status = "Company added";
		HttpStatus httpstatus = HttpStatus.CREATED;
		companyService.addCompany(companyDto);
		return ResponseEntity.status(httpstatus).body(status);
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<String> removeCompany(@PathVariable String name) throws NotFoundException {

		String status = "Company deleted";
		HttpStatus httpstatus = HttpStatus.NO_CONTENT;
		companyService.removeCompany(name);
		return ResponseEntity.status(httpstatus).body(status);
	}
}
