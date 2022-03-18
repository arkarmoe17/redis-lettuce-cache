package com.example.springbootredis.model.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OtpVerifyRequest {
    private String mobile;
    private String otp;
}
