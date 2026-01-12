package project.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// Roles are not yet implemented into this, as part of how this works we need isAdmin
@Component
public class TokenUtil {
    // This should be static but mockito is rejecting TokenUtil if it is static
    private final SecretKey key = Keys.hmacShaKeyFor("RandomN0nScent5".getBytes(StandardCharsets.UTF_8));
    private final int tokenLifetime = 43200000;
    public String tokenMaker(String username){
        long currentTime = System.currentTimeMillis();
        Date expireTime = new Date(currentTime + tokenLifetime);
        JwtBuilder build = Jwts.builder().setSubject(username).setIssuedAt(new Date(currentTime)).setExpiration(expireTime).signWith(SignatureAlgorithm.HS256, key);
        return build.compact();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public TokenUtil() {
    }
}