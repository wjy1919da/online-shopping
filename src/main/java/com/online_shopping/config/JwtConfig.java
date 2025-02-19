package com.online_shopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

    // Reading jwt.secret from application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Create a SecretKey from the jwt.secret
    public SecretKey getSecretKey() {
        // Convert the JWT secret into a SecretKey object
        System.out.println("jwtSecret "+jwtSecret);
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        return new SecretKeySpec(decodedKey, "HmacSHA512");
    }
}

