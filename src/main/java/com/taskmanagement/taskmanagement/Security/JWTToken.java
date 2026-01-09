package com.taskmanagement.taskmanagement.Security;

import com.taskmanagement.taskmanagement.Entity.UserAuth;
import com.taskmanagement.taskmanagement.Enums.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<Permission> permissions=PermissionConfig.getRolePermissions().get(user.getRole());

        Date now = new Date();
        Date expire = new Date(now.getTime() + expireToken);

        return Jwts.builder().setSubject(user.getUserOfficialEmail())
                .claim("role",user.getRole().name())
                .claim("permissions",permissions.stream().map(Enum::name).collect(Collectors.toList()))
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

    public Claims getClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUserEmail(String token) {
        return getClaim(token).getSubject();
    }

}
