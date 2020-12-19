package com.keshar.rolebasedauthentication.controller;

import com.keshar.rolebasedauthentication.entity.User;
import com.keshar.rolebasedauthentication.model.AuthRequest;
import com.keshar.rolebasedauthentication.services.UserServiceImpl;
import com.keshar.rolebasedauthentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.keshar.rolebasedauthentication.common.UserConstants.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid Username or Password!");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @PostMapping("/join")
    public String joinGroup(@RequestBody User user) {
        user.setRoles(DEFAULT_ROLE);
        String password_encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(password_encoded);
        userService.save(user);
        return "Hi " + user.getUserName() + " welcome to group!";
    }

    //if loggedin user is ADMIN-->ADMIN OR MODERATOR
    //if loggedin user is MODERATOR--> MODERATOR


    @GetMapping("/access/{userId}/{userRole}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String giveAccessTOUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) throws UsernameNotFoundException {
        User user = userService.findById(userId).get();
        List<String> activeRoles = getRolesByLoggedInUser(principal);
        String newRole = "";
        if (activeRoles.contains(userRole)) {
            newRole = user.getRoles() + "," + userRole;
            user.setRoles(newRole);
        }
        userService.save(user);
        return "Hi " + user.getUserName() + " New Role assign to you by " + principal.getName();
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> loadUsers() {
        return userService.findAll();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess() {
        return "user can only access this!";
    }

    private List<String> getRolesByLoggedInUser(Principal principal) {
        String roles = getLoggedInUser(principal).getRoles();
        List<String> rolesArray = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (rolesArray.contains("ROLE_ADMIN")) {
            return Arrays.stream(ADMIN_ACCESS).collect(Collectors.toList());
        }
        if (rolesArray.contains("ROLE_MODERATOR")) {
            return Arrays.stream(MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private User getLoggedInUser(Principal principal) {
        return userService.findBYUserName(principal.getName()).get();
    }
}
