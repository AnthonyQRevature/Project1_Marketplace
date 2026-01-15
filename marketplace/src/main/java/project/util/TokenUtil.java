package project.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// Roles are not yet implemented into this, as part of how this works we need isAdmin
@Component
public class TokenUtil {
    public static class Token
    {
        Jws<Claims> token;

        public Token(Jws<Claims> token) {
            this.token = token;
        }

        public String getUsername(){
            return token.getBody().getSubject();
        }
        public Date getExpiration(){
            return token.getBody().getExpiration();
        }
        public boolean isExpired(){
            return token.getBody().getExpiration().after(new Date());
        }

        public boolean isValid()
        {
            return token != null;
        }
    }

    private final SecretKey key = Keys.hmacShaKeyFor("HVmw2nb7Zgbo0BLtoY3Iv7Sh2CnEgfUd".getBytes(StandardCharsets.UTF_8));
    private final int tokenLifetime = 43200000;

    public Token asToken(String token) 
    {
        try{
            var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return new Token(claims);
            
        }catch(Exception e){
            return new Token(null);
        }
    }

    public String makeToken(String username){
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