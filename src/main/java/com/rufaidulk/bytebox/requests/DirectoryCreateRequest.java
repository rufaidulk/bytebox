package com.rufaidulk.bytebox.requests;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

@Component
public class DirectoryCreateRequest 
{
    @NotEmpty
    public String name;

    public Integer parentId;
}
