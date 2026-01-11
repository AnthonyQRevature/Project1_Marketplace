package project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Echo {
    
    @PostMapping("/echo")
    @CrossOrigin() //theres supposed to be a parameter for @CrossOrigin but i didnt get it right last time
    //ill deal with it later
    public String echo(@RequestBody String body)
    {
        return body;
    }
}
