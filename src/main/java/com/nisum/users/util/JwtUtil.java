package com.nisum.users.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createApiToken(String email) {
        Map<String, Object> claims = Jwts.claims().setSubject(email);
        Date dateNow = new Date();
        Date dateExpired = new Date(dateNow.getTime() + 3600000);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(dateNow)
                   .setExpiration(dateExpired)
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getUserNameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e) {
            return Strings.EMPTY;
        }
    }
}
