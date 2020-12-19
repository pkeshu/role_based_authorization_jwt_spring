package com.keshar.rolebasedauthentication.repository;

import com.keshar.rolebasedauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepositoty extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);

}
