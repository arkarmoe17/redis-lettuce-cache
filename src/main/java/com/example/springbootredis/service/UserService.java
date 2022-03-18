package com.example.springbootredis.service;

import com.example.springbootredis.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> saveUser(User user);

    ResponseEntity<?> findAll();

    ResponseEntity<?> findById(Long id);
}
