package com.pm.authservice.service;


import com.pm.authservice.dtos.LoginRequestDTO;
import com.pm.authservice.repository.UserRepository;
import com.pm.authservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class AuthService {




    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }



    public Optional<String> login(LoginRequestDTO loginRequestDTO){
        Optional<String> token = userRepository.findByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u-> jwtUtil.generateToken(u.getEmail() , u.getRole()));

        return token;

    }


    public boolean validateToken(String substring) {

        try {
            jwtUtil.validateToken(substring);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
