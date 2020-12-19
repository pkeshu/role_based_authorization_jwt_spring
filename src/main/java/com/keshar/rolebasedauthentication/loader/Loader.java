package com.keshar.rolebasedauthentication.loader;

import com.keshar.rolebasedauthentication.entity.User;
import com.keshar.rolebasedauthentication.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Loader {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadData() {
        System.out.println("Data Inputing");
//        userService.saveAll(getData());
        System.out.println("Data Loaded...");
    }

    private List<User> getData() {

        return Stream.of(
                new User("keshar", passwordEncoder.encode("keshar"), true, "ROLE_ADMIN"),
                new User("santosh", passwordEncoder.encode("password"), true, "ROLE_USER"),
                new User("gk", passwordEncoder.encode("password1"), true, "ROLE_MODERATOR")
        ).collect(Collectors.toList());
    }

}
