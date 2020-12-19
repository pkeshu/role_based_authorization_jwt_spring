package com.keshar.rolebasedauthentication.services;

import com.keshar.rolebasedauthentication.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findBYUserName(String username);

    Optional<User> findById(int id);

    User save(User user);

    List<User> findAll();

    List<User> saveAll(List<User> data);
}
