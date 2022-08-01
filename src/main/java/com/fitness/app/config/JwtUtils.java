package com.fitness.app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    @Autowired
    Environment environment;

    private String secretKEY = System.getenv("SECRET_KEY");

    /***
     * This function is used to extract Username from the token
     * 
     * @param token
     * @return extract claim
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This function is used to extract expiration date from the token
     * 
     * @param token
     * @return extract claim
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * This function is used to extract claim from the token
     * 
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            log.error("JwtUtils >> extractClaim >>JWT Token Expired");
            return null;
        }
    }

    /**
     * This function is used to extract all claims from the token
     * 
     * @param token
     * @return claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKEY).parseClaimsJws(token).getBody();
    }

    /**
     * This function is used to generate token
     * 
     * @param userDetails
     * @return Token as a string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * This function is used to create token
     * 
     * @param claims
     * @param subject
     * @return Token as a string
     */
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(SignatureAlgorithm.HS256, secretKEY).compact();
    }

    /**
     * This function is used to check whether token is valid or not
     * 
     * @param token
     * @param userDetails
     * @return Boolean - true, if token is valid or else false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        if (username != null) {
            return (username.equals(userDetails.getUsername()));
        } else {
            return false;
        }
    }
}
