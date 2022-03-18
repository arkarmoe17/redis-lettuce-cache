package com.example.springbootredis.externalService;

import com.example.springbootredis.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserRedisService {
    private final CacheService cacheService;

    public User get(String key) {
        User user = cacheService.getUserStatefulRedisConnection().sync().get(key);
        if (user != null) {
            System.out.println("CACHE HIT.");
        } else {
            System.out.println("CACHE MISS.");
        }
        return user;
    }

    public void set(User user) {
        long ttl = 360L;
        cacheService.getUserStatefulRedisConnection().sync().setex(String.valueOf(user.getId()), ttl, user);
    }

    public void setToLists(User user) {
        cacheService.getUserStatefulRedisConnection().sync().lpush("users", user);
    }

    public List<User> getFromLists() {
        List<User> users = cacheService.getUserStatefulRedisConnection().sync().lrange("users", 0, -1);
        return users;
    }

    public void setHash(User user) {
        cacheService.getUserStatefulRedisConnection().sync().hset("userHash", String.valueOf(user.getId()), user);
    }

    public Map<String, User> getHash() {
        Map<String, User> users = (Map<String, User>) cacheService.getUserStatefulRedisConnection().sync().hgetall("userHash");
        return users;
    }

    public User getHashById(String id) {
        User user = null;
        boolean hexists = cacheService.getUserStatefulRedisConnection().sync().hexists("userHash", id);
        System.out.println("existing :" + hexists);
        if (hexists) {
            user = cacheService.getUserStatefulRedisConnection().sync().hget("userHash", id);
            return user;
        }
        return user;
    }

    public Long deleteHashById(String id){
        Long flag=null;
        boolean hexists = cacheService.getUserStatefulRedisConnection().sync().hexists("userHash", id);
        System.out.println("existing :" + hexists);
        if (hexists) {
            flag = cacheService.getUserStatefulRedisConnection().sync().hdel("userHash", id);
            System.out.println("flag : "+flag);
            return flag;
        }
        return flag;
    }


}
