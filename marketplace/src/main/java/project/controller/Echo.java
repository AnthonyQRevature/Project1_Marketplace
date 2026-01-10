package project.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Echo {
    
    @PostMapping("/echo")
    public String echo(@RequestBody String body)
    {
        return body;
    }
}
