package com.rufaidulk.bytebox.controllers;

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
        return new ResponseEntity<>("Welcome to bytebox", HttpStatus.OK);
    }
}
