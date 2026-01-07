package project.util;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//make bean
@Component
public class Hasher {
    //simplicity demo
    public String hashPassword(String input) {

        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
        String hashed = bCryptEncoder.encode(input);
        if (bCryptEncoder.matches(input, hashed)) {
            return hashed;
        }

        return input;
    }
    //validate a login attempt
    public boolean verifyPassword(String hashedUserPassword, String unhashedInputPassword){
        unhashedInputPassword = hashPassword(unhashedInputPassword); //hash input password for comparison to stored data
        return (hashedUserPassword.equals(unhashedInputPassword));
    }
}
