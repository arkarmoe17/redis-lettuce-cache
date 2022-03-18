package com.example.springbootredis.service;

import com.example.springbootredis.model.req.OtpVerifyRequest;
import org.springframework.http.ResponseEntity;

public interface OtpService {
    ResponseEntity<Object> getOTP(String msisdn);
    ResponseEntity<Object> verifyOTP(OtpVerifyRequest request);
}
