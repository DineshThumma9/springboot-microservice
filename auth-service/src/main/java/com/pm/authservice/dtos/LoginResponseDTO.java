package com.pm.authservice.dtos;




public class LoginResponseDTO {



    private final String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }



}
