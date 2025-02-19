package com.online_shopping.security;

import com.online_shopping.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //private final String SECRET_KEY = "mySecretKey";  // Change this in production

     private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generate a secure key
//    private final SecretKey secretKey;
//
//    @Autowired
//    public JwtTokenProvider(JwtConfig jwtConfig) {
//        this.secretKey = jwtConfig.getSecretKey();
//    }

    public String createToken(String username) {
        System.out.println("createToken "+username);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5000 * 60 * 60))  // 1 hour
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("validateToken : " + token);
            System.out.println("SECRET_KEY : " + SECRET_KEY);
            // Use the parserBuilder method instead of the deprecated Jwts.parser() method
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);  // This will throw exceptions if the token is invalid

            // If the token is valid, we return true
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT token.");
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature.");
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token.");
        }
        return false;
    }
}

