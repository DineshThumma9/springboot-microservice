package com.example.demo.dtos;


import com.example.demo.dtos.validaters.CreatePatientValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class PatientRequestDTO {


    @NotBlank(message = "Name cannot be null")
    @Size(max = 100, message = "Name should have not exceeded 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Address cannot be null")
    private String address;

    @NotBlank(message = "Date of birth cannot be null")
    private String dateOfBirth;


    @NotBlank(groups = CreatePatientValidationGroup.class , message = "Registered date cannot be null")
    private String registeredDate;




    public @NotBlank(message = "Name cannot be null") @Size(min = 2, max = 100, message = "Name should have not exceeded 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be null") @Size(max = 2, message = "Name should have not exceeded 100 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email cannot be null") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be null") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Address cannot be null") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address cannot be null") String address) {
        this.address = address;
    }

    public @NotBlank(message = "Date of birth cannot be null") String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotBlank(message = "Date of birth cannot be null") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
