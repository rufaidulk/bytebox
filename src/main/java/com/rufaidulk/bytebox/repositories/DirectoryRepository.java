package com.rufaidulk.bytebox.repositories;

import com.rufaidulk.bytebox.models.Directory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends CrudRepository<Directory, Integer>
{
    
}
