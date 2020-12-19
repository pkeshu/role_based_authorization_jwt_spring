package com.keshar.rolebasedauthentication.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@ToString
@Entity(name = "TBL_USER_AUTH")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String password;
    private boolean active;
    private String roles;

    public User() {
    }

    public User(String userName, String password, boolean active, String roles) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
}
