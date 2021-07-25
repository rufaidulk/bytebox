package com.rufaidulk.bytebox.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.rufaidulk.bytebox.exceptions.ValidationException;
import com.rufaidulk.bytebox.repositories.UserRepository;

public class RegisterRequest 
{
    @NotEmpty
    public String name;

    @Email
    public String email;

    @NotEmpty
    public String password;

    private UserRepository userRepository;

    public void validate(UserRepository userRepository) throws ValidationException
    {
        this.userRepository = userRepository;
        validateEmailAlreadyExist();
    }

    private void validateEmailAlreadyExist() 
    {
        if (this.userRepository.findByEmail(this.email).isPresent()) {
            throw new ValidationException("email", "already exist");
        }
    }
}
