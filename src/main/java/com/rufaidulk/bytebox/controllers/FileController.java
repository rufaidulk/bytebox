package com.rufaidulk.bytebox.controllers;

import java.util.HashMap;
import java.util.Map;

import com.rufaidulk.bytebox.AppUserDetails;
import com.rufaidulk.bytebox.models.Box;
import com.rufaidulk.bytebox.repositories.BoxRepository;
import com.rufaidulk.bytebox.repositories.FileRepository;
import com.rufaidulk.bytebox.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController 
{
    @Autowired BoxRepository boxRepository;

    @Autowired FileRepository fileRepository;

    @Autowired FileService fileService;
    
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createDirectory(@RequestParam String name, 
                                                @RequestParam MultipartFile file,
                                                @RequestParam Integer directoryId)
    {   
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal();
        Box box = boxRepository.findByUserId(appUserDetails.getId()).get();

        fileService.setRequestParams(name, file, directoryId);
        fileService.handle(box);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("id", name);
        response.put("dirid", directoryId);
        response.put("file", StringUtils.cleanPath(file.getOriginalFilename()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
