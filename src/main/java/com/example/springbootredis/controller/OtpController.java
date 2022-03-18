package com.example.springbootredis.controller;

import com.example.springbootredis.model.req.OtpVerifyRequest;
import com.example.springbootredis.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @GetMapping("/generate")
    public ResponseEntity<Object> generateOtp(@RequestParam("msisdn") String msisdn) {
        return otpService.getOTP(msisdn);
    }

    @PostMapping("/verification")
    public ResponseEntity<Object> verifyOTP(@RequestBody OtpVerifyRequest request) {
        return otpService.verifyOTP(request);
    }

}


