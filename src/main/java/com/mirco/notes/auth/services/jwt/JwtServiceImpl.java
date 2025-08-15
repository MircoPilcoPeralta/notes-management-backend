package com.mirco.notes.auth.services.jwt;

import com.mirco.notes.auth.model.entities.SystemUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${aplication.security.jwt.secret-key}")
    private String secretKey;

    @Value("${aplication.security.jwt.expiration}")
    private Long jwtExp;

    @Value("${aplication.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExp;


    @Override
    public String generateToken(SystemUser systemUser) {
        return buildToken(systemUser, jwtExp);
    }

    @Override
    public String extractUsername(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    @Override
    public Date extractExpirationDate(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getExpiration();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }


    @Override
    public Boolean isTokenValid(String jwt, SystemUser systemUser) {
        final String email = extractUsername(jwt);
        return (email.equals(systemUser.getEmail()) && !isTokenExpired(jwt));
    }

    private String buildToken(SystemUser systemUser, Long jwtExp) {
        Map<String, String> payload = Map.of("fullName", systemUser.getFullName());

        return Jwts.builder()
                .setId(systemUser.getId().toString())
                .setClaims(payload)
                .setSubject(systemUser.getEmail())
                .setExpiration(new Date(System.currentTimeMillis()  + jwtExp))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
