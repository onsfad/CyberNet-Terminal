package com.austine.blog.repository;

import com.austine.blog.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    public Optional<User> findUsersById(Long id);

    public void deleteUserById(Long id);
}