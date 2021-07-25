package com.rufaidulk.bytebox.repositories;

import java.util.Optional;

import com.rufaidulk.bytebox.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
    public Optional<User> findByEmail(String email);
}
