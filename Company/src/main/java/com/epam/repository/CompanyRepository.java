package com.epam.repository;

import com.epam.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company,String>{

    public Company findByName(String name);
    public boolean existsByName(String name);
		
}