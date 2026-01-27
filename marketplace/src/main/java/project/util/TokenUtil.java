package project.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

// Roles are not yet implemented into this, as part of how this works we need isAdmin
@Component
public class TokenUtil {
    @ConfigurationProperties("security")
    public static class TokenProperties
    {
        private String secret;

        public TokenProperties() {
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    private final SecretKey key;
    private final int tokenLifetime = 43200000;

    public static class Token
    {
        private final Jws<Claims> token;

        public Token(Jws<Claims> token) {
            this.token = token;
        }

        public int getId() {
            return Integer.parseInt(token.getBody().getId());
        }
        public String getUsername(){
            return token.getBody().getSubject();
        }
        public Date getExpiration(){
            return token.getBody().getExpiration();
        }
        public boolean isExpired(){
            return token.getBody().getExpiration().before(new Date());
        }
        public Jws<Claims> getToken() {return token;}

        public boolean isValid()
        {
            return token != null;
        }
    }

    public Token asToken(String token) 
    {
        try{
            var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return new Token(claims);
            
        }catch(ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e){
            return new Token(null);
        }
    }

    public String makeToken(String username, Integer id){
        long currentTime = System.currentTimeMillis();
        Date expireTime = new Date(currentTime + tokenLifetime);
        JwtBuilder build = Jwts.builder().setSubject(username).setId(id.toString()).setIssuedAt(new Date(currentTime)).setExpiration(expireTime).signWith(key, SignatureAlgorithm.HS256);
        return build.compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e){
            return false;
        }
    }

    @Autowired
    public TokenUtil(TokenProperties properties) {
        key = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}