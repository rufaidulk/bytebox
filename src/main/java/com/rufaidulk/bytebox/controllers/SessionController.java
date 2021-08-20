package com.rufaidulk.bytebox.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.rufaidulk.bytebox.repositories.UserRepository;
import com.rufaidulk.bytebox.requests.LoginRequest;
import com.rufaidulk.bytebox.requests.RegisterRequest;
import com.rufaidulk.bytebox.services.JwtService;
import com.rufaidulk.bytebox.services.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController 
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/registers")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request)
    {
        request.validate(userRepository);
        registrationService.setRegisterRequest(request);
        registrationService.handle();

        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCCESS");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request)
    {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email, request.password));
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("token", jwtService.generateToken(request.email));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
