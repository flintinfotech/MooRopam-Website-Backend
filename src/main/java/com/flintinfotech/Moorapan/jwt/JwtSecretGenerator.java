package com.flintinfotech.Moorapan.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        System.out.println(generateSecret());
    }

    private static final int SECRET_LENGTH = 32;  // 256 bits

    public static String generateSecret() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[SECRET_LENGTH];
        secureRandom.nextBytes(secretBytes);
        return Base64.getEncoder().encodeToString(secretBytes);
    }
}