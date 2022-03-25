package com.epam.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class EmployeeDto {

    @NotBlank(message = "FirstName cannot be empty or null")
    String firstname;
    @NotBlank(message = "SecondName cannot be empty or null")
    String secondName;
    @NotBlank(message = "Company Name cannot be empty or null")
    String company;
    @Positive(message = "Age cannot be negative or zero or null")
    int age;

    public EmployeeDto() {
        super();
    }

    public EmployeeDto(String firstname, String secondName, String company, int age) {
        super();
        this.firstname = firstname;
        this.secondName = secondName;
        this.company = company;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
