package com.example.managementsystem.config;

import com.example.managementsystem.models.entities.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserEmailFromToken(String token) {
        String email = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        logger.info("Extracted email from token: {}", email);
        return email;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            logger.info("Token is valid");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Token has expired");
            return false;
        } catch (Exception ex) {
            logger.error("Token validation failed: {}", ex.getMessage());
            return false;
        }
    }


    public List<String> getAuthoritiesFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("authorities", List.class);
    }


}