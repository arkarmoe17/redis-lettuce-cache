package com.example.springbootredis.utils;

import java.util.Random;

public class CommonUtils {

    /**
     * Generate OTP
     **/
    public static String generateOTP(int length) {
        String numbers = "0123456789";
        Random random = new Random();

        StringBuilder otpStr = new StringBuilder();
        char[] otp = new char[length];
        for (int i = 0; i < otp.length; i++) {
            otpStr.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otpStr.toString();
    }
}
