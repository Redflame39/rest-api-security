package com.epam.esm.security.impl;

import com.epam.esm.security.api.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {

    private static final int TOKEN_EXPIRATION_DAYS = 5;
    @Value("${spring.security.oauth2.client.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(String username, String password) {
        Date expirationDate = Date.from(
                LocalDate.now()
                        .plusDays(TOKEN_EXPIRATION_DAYS)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant());
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Attempting to parse invalid JWT", e);
        } catch (Exception e) {
            log.error("Exception has been thrown while validating JWT", e);
        }
        return false;
    }

    @Override
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
