package com.Cs681.Game.Util;


import java.sql.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	 private static final String SECRET_KEY = "Nishith";
	    
	    public  String generateToken(String userName) {
	        long currentTimeMillis = System.currentTimeMillis();
	        long expirationTimeMillis = currentTimeMillis + 86400000; // Token expires in 24 hours
	        String jwtToken = Jwts.builder()
	                .setSubject(userName)
	                .setIssuedAt(new Date(currentTimeMillis))
	                .setExpiration(new Date(expirationTimeMillis))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	                .compact();
	        return jwtToken;
}
	    public String getUserName(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(SECRET_KEY)
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	    }
}

