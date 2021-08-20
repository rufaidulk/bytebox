package com.rufaidulk.bytebox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.rufaidulk.bytebox.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails
{
    private int id;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public AppUserDetails(User user) 
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return this.authorities;
    }

    public int getId()
    {
        return this.id;
    }

    @Override
    public String getPassword() 
    {
        return this.password;
    }

    @Override
    public String getUsername() 
    {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() 
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() 
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() 
    {
        return true;
    }

    @Override
    public boolean isEnabled() 
    {
        return true;
    }

}
