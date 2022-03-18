package com.example.springbootredis.service.impl;

import com.example.springbootredis.externalService.CacheService;
import com.example.springbootredis.model.req.OtpVerifyRequest;
import com.example.springbootredis.service.OtpService;
import com.example.springbootredis.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final Jedis jedis = new Jedis();
    private final CacheService cacheService;

    @Override
    public ResponseEntity<Object> getOTP(String msisdn) {
        Map<String, String> response = new HashMap<>(2);

        String otp = CommonUtils.generateOTP(4);
        System.out.println("otp: " + otp);

//        jedis.setex(msisdn, 180, otp);
        cacheService.getStringStatefulRedisConnection().sync().setex(msisdn,180,otp);

        response.put("status", "success");
        response.put("message", "OTP " + otp + " is successfully generated.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> verifyOTP(OtpVerifyRequest req) {
        Map<String, String> response = new HashMap<>(2);
        System.out.println("request: " + req.toString());

//        String value = jedis.get(req.getMobile());
        String value =  cacheService.getStringStatefulRedisConnection().sync().get(req.getMobile());
        System.out.println("value: "+value);
        if (value != null) {
            if (value.equals(req.getOtp())) {
                response.put("status", "success");
                response.put("message", "OTP code is correct.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.put("status", "fail");
                response.put("message", "OTP code is wrong.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.put("status", "fail");
        response.put("message", "OTP code is already expired.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
