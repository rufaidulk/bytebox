package com.rufaidulk.bytebox.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.rufaidulk.bytebox.AppUserDetails;
import com.rufaidulk.bytebox.exceptions.ResourceNotFoundException;
import com.rufaidulk.bytebox.models.Box;
import com.rufaidulk.bytebox.models.Directory;
import com.rufaidulk.bytebox.repositories.BoxRepository;
import com.rufaidulk.bytebox.requests.DirectoryCreateRequest;
import com.rufaidulk.bytebox.services.DirectoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directory")
public class DirectoryController 
{
    @Autowired BoxRepository boxRepository;

    private DirectoryService directoryService;
    
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDirectory(@RequestBody DirectoryCreateRequest request)
    {   
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal();
        Optional<Box> boxOptional = boxRepository.findByUserId(appUserDetails.getId());
        Box box = boxOptional.orElseThrow(() -> new ResourceNotFoundException("box not found."));

        directoryService.setRequest(request);
        Directory directory = directoryService.createDirectory(box);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("id", directory.getId());
        response.put("name", directory.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
