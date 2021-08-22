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

    private Directory parentDirectory;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository) 
    {
        this.directoryRepository = directoryRepository;
    }

    public void setRequest(DirectoryCreateRequest request) 
    {
        this.request = request;
    }

    public Directory handle(Box box)
    {
        Directory directory = this.createDirectory(box);
        if (this.request.parentId == null) {
            StorageService.createDirectory(box.getName(), this.request.name);
        }
        else {
            StorageService.createDirectory(box.getName() + "/" + this.getParentDirectory().getFullPath(), 
                this.request.name);
        }

        return directory;
    }

    private Directory createDirectory(Box box)
    {
        Directory directory = new Directory();
        directory.setParentId(request.parentId);
        directory.setName(request.name);
        directory.setBox(box);
        if (this.request.parentId == null) {
            directory.setFullPath(request.name);
        }
        else {
            directory.setFullPath(this.getParentDirectory().getFullPath() + "/" + request.name);
        }

        directoryRepository.save(directory);

        return directory;
    }

    private Directory getParentDirectory()
    {
        if (this.parentDirectory == null) {
            this.parentDirectory = directoryRepository.findById(this.request.parentId).get();
        }

        return this.parentDirectory;
    }

}
