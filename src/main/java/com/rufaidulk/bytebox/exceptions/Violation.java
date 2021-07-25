package com.rufaidulk.bytebox.exceptions;

import lombok.Data;

@Data
public class Violation 
{
    private final String fieldName;
    private final String message;    
}
