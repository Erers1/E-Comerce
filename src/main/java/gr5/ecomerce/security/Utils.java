package gr5.ecomerce.security;

import gr5.ecomerce.entity.Role;
import gr5.ecomerce.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class Utils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.accessExpiration}")
    private Long accessExpiration;
    @Value("${jwt.refreshExpiration}")
    private Long refreshExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Long id, String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setId(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+accessExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long id, String username) {
        return Jwts.builder()
                .setSubject(username)
                .setId(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+refreshExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getId();
    }

    public boolean isExpireDate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().get("role", String.class);
    }

    public boolean validate(String token, User user) {
        return extractId(token).equals(user.getId().toString())
                && !isExpireDate(token);
    }

    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
