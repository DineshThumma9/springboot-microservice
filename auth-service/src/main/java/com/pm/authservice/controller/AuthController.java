package com.pm.authservice.controller;


import com.pm.authservice.dtos.LoginRequestDTO;
import com.pm.authservice.dtos.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @Operation(summary = "Login", description = "Login to the application")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO) {


        Optional<String> tokenOptional = authService.login(loginRequestDTO);

        if (tokenOptional.isPresent()) {
            String token = tokenOptional.get();
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token);
            return ResponseEntity.ok(loginResponseDTO);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

    }

    @Operation(summary = "Validate Token", description = "Validate the JWT token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
        }



        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized



    }

}
