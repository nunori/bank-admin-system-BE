package com.im.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 120;

    private final Environment env;
    private String secretKey;

    public JwtUtil(Environment env) {
        this.env = env;
    }

    @PostConstruct
    private void init() {
        this.secretKey = env.getProperty("jwt.secret");
        System.out.println("Loaded JWT Secret Key: " + secretKey); // 테스트용 출력, 이후 보안을 위해 제거
    }

    public String generateToken(String userNumber, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractUserNumber(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String userNumber) {
        Claims claims = extractClaims(token);
        String role = (String) claims.get("role");
        return (userNumber.equals(extractUserNumber(token)) && !isTokenExpired(token) && role != null);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
