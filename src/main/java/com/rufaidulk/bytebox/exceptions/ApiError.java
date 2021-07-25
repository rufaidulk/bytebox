package com.rufaidulk.bytebox.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError 
{
    private HttpStatus status;
    
    private String message;

    private List<Violation> violations = new ArrayList<>();

}
