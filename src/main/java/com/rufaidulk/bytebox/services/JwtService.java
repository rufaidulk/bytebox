package com.rufaidulk.bytebox.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService 
{
    private final String JWT_SECRET = "bytebox-jwt-secret";

    public String generateToken(String subject)
    {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                    .signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
    }

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) 
    {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, String username) 
    {
        final String subject = extractUsername(token);
        return (subject.equals(username) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) 
    {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) 
    {
        return extractExpiration(token).before(new Date());
    }    
}
