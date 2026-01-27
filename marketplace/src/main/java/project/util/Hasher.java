package project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//make bean
@Component
public class Hasher {
    BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
    
    /**
     * Salts and Hashes a password using bCrypt
     * @param input
     * @return A string that contains the salt and the hash of the password. 
     * The result is not guarenteed to be the same with identical calls to this function.
     * The Password must therefore be compared using verifyPassword
     */
    public String hashPassword(String input) {
        String hashed = bCryptEncoder.encode(input);
        if (bCryptEncoder.matches(input, hashed)) {
            return hashed;
        }

        return input;
    }
    
    /**
     * Verifies that the hash was generated using the password
     * @param hashedUserPassword The hash of the password to be verified.
     * @param unhashedInputPassword The password to verify
     * @return true if the hash was generated using the given password. false otherwise.
     */
    public boolean verifyPassword(String hashedUserPassword, String unhashedInputPassword){
        return bCryptEncoder.matches(unhashedInputPassword, hashedUserPassword);
    }
}
