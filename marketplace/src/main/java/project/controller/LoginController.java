package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.controller.model.UserModel;
import project.service.UserService;

/*
 * A Controller for the /login endpoint
 */
@RestController
public class LoginController {

    UserService userService;

    /*
     * Login should take a UserModel with only the username and password filled in
     * on success return some sort of session token to prove that the user logged in (TBD)
     * on fail return some sort of failure.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel body)
    {
        return ResponseEntity.internalServerError().build();
    }

    /*
     * RegisterUser should take in a UserModel with the username, password, and email filled in 
     * (whatever data we need to create a new user)
     * then persist it to the Database
     * and return a UserModel with the username, email, and createdAt date filled in.
     */
    @PutMapping("/login")
    @CrossOrigin()
    public ResponseEntity<UserModel> registerUser(@RequestBody UserModel body)
    {
        System.out.printf("Recieved: %s\n", body.toString());

        var response = userService.registerNewUser(body);
        return response;
    }

    @Autowired
    public LoginController(UserService userService) { this.userService = userService; }
}
