package com.taskmanagement.taskmanagement.Security;

import com.taskmanagement.taskmanagement.Entity.UserAuth;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTToken {
    private final Key key;
    private final Long expireToken=1000L*60*60*24;

    public JWTToken() {
        String secretKey = System.getenv("JWT_SECRET_KEY");
        if(secretKey==null || secretKey.isBlank()) {
            secretKey="ReplaceThisWithSomeSecretKey";
        }
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserAuth user) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + expireToken);

        return Jwts.builder().setSubject(user.getUserOfficialEmail())
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

}
