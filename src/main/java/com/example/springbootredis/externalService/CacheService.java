package com.example.springbootredis.externalService;

import com.example.springbootredis.codec.SecurityQuestionSerializationCodec;
import com.example.springbootredis.codec.TypeValueCodec;
import com.example.springbootredis.codec.UserSerializationCodec;
import com.example.springbootredis.model.entity.SecurityQuestion;
import com.example.springbootredis.model.entity.User;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Data
public class CacheService {
    private final String url = "redis://localhost:6379/1";
    private RedisClient redisClient = null;

    private StatefulRedisConnection<String, String> stringStatefulRedisConnection = null;
    private StatefulRedisConnection<String, User> userStatefulRedisConnection = null;
    private StatefulRedisConnection<String, Object> typeValueRedisConnection = null;
    private StatefulRedisConnection<String, SecurityQuestion> securityQuestionStatefulRedisConnection = null;


    @PostConstruct
    private void init() {
        System.out.println("post construct ...");
        redisClient = RedisClient.create(url);
        stringStatefulRedisConnection = redisClient.connect();
        userStatefulRedisConnection = redisClient.connect(new UserSerializationCodec());
        typeValueRedisConnection = redisClient.connect(new TypeValueCodec());
        securityQuestionStatefulRedisConnection = redisClient.connect(new SecurityQuestionSerializationCodec());
    }

    @PreDestroy
    private void destroy() {
        System.out.println("pre destroy ...");
        if (stringStatefulRedisConnection != null) {
            stringStatefulRedisConnection.close();
        }
        if (userStatefulRedisConnection != null) {
            userStatefulRedisConnection.close();
        }
        if (typeValueRedisConnection != null) {
            typeValueRedisConnection.close();
        }
        if (securityQuestionStatefulRedisConnection != null) {
            securityQuestionStatefulRedisConnection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }
}
