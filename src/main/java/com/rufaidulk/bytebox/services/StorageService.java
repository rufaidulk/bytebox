package com.rufaidulk.bytebox.services;

import java.io.File;

import com.rufaidulk.bytebox.models.Box;

import org.springframework.stereotype.Service;

@Service
public class StorageService 
{
    private final static String ROOT_LOCATION = "./uploads";

    public static boolean createDirectory(String basePath, String name)
    {
        File file = new File(ROOT_LOCATION + "/" + basePath, name);
        if (file.exists() == false) {
            return file.mkdir();
        }
        
        return true;
    }

    public static boolean createBox(Box box)
    {
        File file = new File(ROOT_LOCATION + "/", box.getName());
        if (file.exists() == false) {
            return file.mkdir();
        }
        
        return true;
    }
}
