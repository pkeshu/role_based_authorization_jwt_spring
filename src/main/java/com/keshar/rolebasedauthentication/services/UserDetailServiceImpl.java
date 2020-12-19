package com.keshar.rolebasedauthentication.services;

import com.keshar.rolebasedauthentication.entity.User;
import com.keshar.rolebasedauthentication.repository.UserRepositoty;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserServiceImpl service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUserName = service.findBYUserName(username);
        return byUserName
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " doesn't exist!"));
    }
}
