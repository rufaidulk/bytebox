package com.rufaidulk.bytebox.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginRequest 
{
    @Email
    public String email;

    @NotEmpty
    public String password;
}
