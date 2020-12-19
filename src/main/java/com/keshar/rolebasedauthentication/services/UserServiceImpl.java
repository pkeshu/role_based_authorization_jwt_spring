package com.keshar.rolebasedauthentication.services;

import com.keshar.rolebasedauthentication.entity.User;
import com.keshar.rolebasedauthentication.repository.UserRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoty repositoty;

    @Override
    public Optional<User> findBYUserName(String username) {
        return repositoty.findByUserName(username);
    }

    @Override
    public Optional<User> findById(int id) {
        return repositoty.findById(id);
    }

    @Override
    public User save(User user) {
        return repositoty.save(user);
    }

    @Override
    public List<User> findAll() {
        return repositoty.findAll();
    }

    @Override
    public List<User> saveAll(List<User> data) {
        return repositoty.saveAll(data);
    }
}
