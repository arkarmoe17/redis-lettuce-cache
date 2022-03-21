package com.example.springbootredis.controller;

import com.example.springbootredis.service.SecurityQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security-questions")
@RequiredArgsConstructor
public class SecurityQuestionController {
    private final SecurityQuestionService securityQuestionService;

    //get all
    @GetMapping()
    public ResponseEntity<?> findAll() {
        return securityQuestionService.findAll();
    }

    //find by Id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")Long id){
        return securityQuestionService.findById(id);
    }
}
