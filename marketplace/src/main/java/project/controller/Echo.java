package project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import project.util.AllowCORS;
import project.util.Secure;
import project.util.SecurityLevel;

@RestController
public class Echo {
    
    @PostMapping("/echo")
    @AllowCORS
    public String echo(@RequestBody String body)
    {
        return body;
    }

    @PostMapping("/secure/echo")
    @AllowCORS
    /**
     * @Secure is an annotation that verifies the first string as a jwt.
     * if the jwt is invalid then responds with status 409
     * otherwise executes the method as normal 
     */
    @Secure(SecurityLevel.USER)
    public ResponseEntity<String> secureEcho(@RequestHeader("Authorization") String authHeader, @RequestBody String body)
    {
        return ResponseEntity.ok(body);
    }
}
