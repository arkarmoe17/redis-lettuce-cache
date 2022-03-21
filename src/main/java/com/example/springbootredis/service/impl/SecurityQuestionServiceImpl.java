package com.example.springbootredis.service.impl;

import com.example.springbootredis.externalService.CacheService;
import com.example.springbootredis.model.entity.SecurityQuestion;
import com.example.springbootredis.repository.SecurityQuestionRepo;
import com.example.springbootredis.service.SecurityQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityQuestionServiceImpl implements SecurityQuestionService {
    private final SecurityQuestionRepo securityQuestionRepo;
    private final CacheService cacheService;

    @Override
    public ResponseEntity<?> findAll() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        Map<String, SecurityQuestion> securityQuestionMap = cacheService.getSecurityQuestionStatefulRedisConnection().sync().hgetall("securityQuestions");
        System.out.println("security Map: " + securityQuestionMap);
        if (securityQuestionMap.isEmpty()) {
            securityQuestions = securityQuestionRepo.findAll();
            for (SecurityQuestion s : securityQuestions) {
                cacheService.getSecurityQuestionStatefulRedisConnection().sync().hset("securityQuestions", String.valueOf(s.getId()), s);
            }
            List<String> keys = cacheService.getSecurityQuestionStatefulRedisConnection().sync().hkeys("securityQuestions");
            keys.forEach(System.out::println);
        } else {
            securityQuestions = new ArrayList<>(securityQuestionMap.values());
        }
        return new ResponseEntity<>(securityQuestions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        SecurityQuestion securityQuestion = null;
        boolean isExist = cacheService.getSecurityQuestionStatefulRedisConnection().sync().hexists("securityQuestions", String.valueOf(id));
        if (isExist) {
            System.out.println("cache hit");
            securityQuestion = cacheService.getSecurityQuestionStatefulRedisConnection().sync().hget("securityQuestions", String.valueOf(id));
        } else {
            System.out.println("cache miss");
            Optional<SecurityQuestion> securityQuestionOptional = securityQuestionRepo.findById(id);
            if (securityQuestionOptional.isPresent())
                securityQuestion = securityQuestionOptional.get();
        }
        return new ResponseEntity<>(securityQuestion, HttpStatus.OK);
    }
}
