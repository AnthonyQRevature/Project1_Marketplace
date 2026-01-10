package project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Echo {
    
    @PostMapping("/echo")
    @CrossOrigin()
    public String echo(@RequestBody String body)
    {
        return body;
    }
}
