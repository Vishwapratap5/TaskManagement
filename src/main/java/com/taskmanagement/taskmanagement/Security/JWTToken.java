package com.taskmanagement.taskmanagement.Security;

import com.taskmanagement.taskmanagement.Entity.UserAuth;
import com.taskmanagement.taskmanagement.Enums.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTToken {

    private Key key;

    private final Long expireToken = 1000L * 60 * 60 * 24;

    @PostConstruct
    public void init() {
        String secretKey = System.getenv("JWT_SECRET_KEY");

        if (secretKey == null || secretKey.isBlank()) {
            // 64 characters = safe
            secretKey = "ThisIsAVeryLongAndSecureSecretKeyForJwtAuthentication123456";
        }

        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserAuth user) {

        Set<Permission> permissions =
                PermissionConfig.getRolePermissions().get(user.getRole());

        Date now = new Date();
        Date expire = new Date(now.getTime() + expireToken);

        return Jwts.builder()
                .setSubject(user.getUserOfficialEmail())
                .claim("role", user.getRole().name())
                .claim("permissions",
                        permissions.stream().map(Enum::name).collect(Collectors.toList()))
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }


}