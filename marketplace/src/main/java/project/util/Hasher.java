package project.util;

import org.springframework.stereotype.Component;

//make bean
@Component
public class Hasher {
    //simplicity demo
    public String HashPassword(String input)
    {
        return input;
    }
}
