package com.example.springbootredis.service;

import org.springframework.http.ResponseEntity;

public interface SecurityQuestionService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
}
