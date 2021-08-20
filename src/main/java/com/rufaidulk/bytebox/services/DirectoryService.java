package com.rufaidulk.bytebox.services;

import com.rufaidulk.bytebox.models.Box;
import com.rufaidulk.bytebox.models.Directory;
import com.rufaidulk.bytebox.repositories.DirectoryRepository;
import com.rufaidulk.bytebox.requests.DirectoryCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService 
{
    private DirectoryRepository directoryRepository;

    private DirectoryCreateRequest request;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository) 
    {
        this.directoryRepository = directoryRepository;
    }

    public void setRequest(DirectoryCreateRequest request) 
    {
        this.request = request;
    }

    public Directory createDirectory(Box box)
    {
        Directory directory = new Directory();
        directory.setParentId(request.parentId);
        directory.setName(request.name);
        directory.setBox(box);
        directoryRepository.save(directory);

        return directory;
    }
}
