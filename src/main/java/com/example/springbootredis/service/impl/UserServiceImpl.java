package com.example.springbootredis.service.impl;

import com.example.springbootredis.externalService.UserRedisService;
import com.example.springbootredis.model.entity.User;
import com.example.springbootredis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRedisService userRedisService;

    @Override
    public ResponseEntity<?> saveUser(User user) {

        /** lists **/
//        List<Object> users = new ArrayList<>();
//        userRedisService.setToLists(user);
//        users = userRedisService.getFromLists();
        /** hash map**/
        Map<String, User> userMap = new HashMap<>();
        userRedisService.setHash(user);
        userMap = userRedisService.getHash();
        return new ResponseEntity<>(userMap, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAll() {
//        List<User> users = userRedisService.getFromLists();
        Map<String, User> userMaps = userRedisService.getHash();
        if (userMaps == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(userMaps, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        User user = userRedisService.getHashById(String.valueOf(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
