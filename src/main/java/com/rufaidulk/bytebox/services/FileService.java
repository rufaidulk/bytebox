package com.rufaidulk.bytebox.services;

import com.rufaidulk.bytebox.models.Box;
import com.rufaidulk.bytebox.models.Directory;
import com.rufaidulk.bytebox.models.File;
import com.rufaidulk.bytebox.repositories.DirectoryRepository;
import com.rufaidulk.bytebox.repositories.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService 
{
    private String name;
    private Integer directoryId;
    private MultipartFile file;
    private DirectoryRepository directoryRepository;
    private Directory directory;
    private FileRepository fileRepository;

    @Autowired
    public FileService(DirectoryRepository directoryRepository, FileRepository fileRepository) 
    {
        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
    }

    public void setRequestParams(String name, MultipartFile file, Integer directoryId)
    {
        this.name = name;
        this.file = file;
        this.directoryId = directoryId;
    }

    /**
     * 
     */
    public void handle(Box box)
    {
        File file = new File();
        file.setName(name);
        
        if (this.directoryId != null) {
            file.setDirectory(this.getDirectory());
            file.setFullPath(this.getDirectory().getFullPath());
        }

        this.fileRepository.save(file);
        StorageService.storeFile(this.file, box.getName() + "/" + file.getFullPath());
    }

    private Directory getDirectory()
    {
        if (this.directory == null) {
            this.directory = this.directoryRepository.findById(this.directoryId).get();
        }

        return this.directory;
    }
}
