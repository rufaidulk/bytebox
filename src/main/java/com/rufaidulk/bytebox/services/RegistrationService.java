package com.rufaidulk.bytebox.services;

import com.rufaidulk.bytebox.models.Box;
import com.rufaidulk.bytebox.models.User;
import com.rufaidulk.bytebox.repositories.BoxRepository;
import com.rufaidulk.bytebox.repositories.UserRepository;
import com.rufaidulk.bytebox.requests.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService 
{
    @Autowired
    private UserRepository userRepository;

    private BoxRepository boxRepository;

    private RegisterRequest registerRequest;

    @Autowired
    public RegistrationService(UserRepository userRepository, BoxRepository boxRepository) 
    {
        this.userRepository = userRepository;
        this.boxRepository = boxRepository;
    }

    public void setRegisterRequest(RegisterRequest registerRequest) 
    {
        this.registerRequest = registerRequest;
    }

    /**
     * Transaction will rollback on runtime exception
     */
    @Transactional
    public void handle()
    {
        try {
            User user = createUser();
            createBox(user);
        } 
        catch (RuntimeException ex) {
            throw ex;
        }
    }

    private User createUser()
    {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = passwordEncoder.encode(registerRequest.password);
        //todo:: replace using ENUM
        byte status = 1;
        User user = new User();
        user.setName(registerRequest.name);
        user.setEmail(registerRequest.email);
        user.setPassword(password);
        user.setStatus(status);
        userRepository.save(user);

        return user;
    }

    private void createBox(User user)
    {
        Box box = new Box();
        box.setName("box-" + user.getId());
        box.setUser(user);
        boxRepository.save(box);

        if (StorageService.createBox(box) == false) {
            throw new RuntimeException("Failed to create box folder");
        }
    }

}
