package com.epam.dto;

public class CompanyDto {
    String name;
    String state;
    String district;
    int pin;

    public CompanyDto(String name, String state, String district, int contact) {
        super();
        this.name = name;
        this.state = state;
        this.district = district;
        this.pin = contact;
    }

    public CompanyDto() {
        super();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int contact) {
        this.pin = contact;
    }

    @Override
    public String toString() {
        return "Company [name=" + name + ", state=" + state + ", district=" + district + ", contact=" + pin + "]";
    }
}
