package com.rufaidulk.bytebox.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.rufaidulk.bytebox.models.Box;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public static String storeFile(MultipartFile file, String fullPath) 
    {
        Path rootLocation = Paths.get(ROOT_LOCATION + "/" + fullPath);
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }

        return filename;
    }
}
