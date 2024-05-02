package com.passakorn.productmanagement.repository;

import com.passakorn.productmanagement.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);
}
