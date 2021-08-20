package com.rufaidulk.bytebox.repositories;

import java.util.Optional;

import com.rufaidulk.bytebox.models.Box;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends CrudRepository<Box, Integer>
{
    Optional<Box> findByUserId(int userId);
}
