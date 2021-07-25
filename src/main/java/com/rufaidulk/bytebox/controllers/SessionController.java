package com.rufaidulk.bytebox.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.rufaidulk.bytebox.models.User;
import com.rufaidulk.bytebox.repositories.UserRepository;
import com.rufaidulk.bytebox.requests.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController 
{
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registers")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request)
    {
        request.validate(userRepository);
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = passwordEncoder.encode(request.password);
        byte status = 1;
        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPassword(password);
        user.setStatus(status);
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCCESS");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
