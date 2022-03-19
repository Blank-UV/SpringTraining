package com.epam.dto;

public class EmployeeDto {

    String firstname;
    String secondName;
    String company;
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
