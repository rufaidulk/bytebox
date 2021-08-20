package com.rufaidulk.bytebox.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController 
{
    @GetMapping("/home")
    public ResponseEntity<?> home()
    {
        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Welcome to bytebox!");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
