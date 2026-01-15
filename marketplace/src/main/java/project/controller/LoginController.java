package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.controller.request.LoginRequest;
import project.controller.request.RegisterRequest;
import project.controller.response.LoginResponse;
import project.service.UserService;
import project.util.AllowCORS;
import project.util.DatabaseConflictException;
import project.util.InvalidCredentialsException;

/*
 * A Controller for the /login endpoint
 */
@RestController
public class LoginController {

    UserService userService;

    /**
     * Login takes a LoginRequest when 
     * on success return some sort of session token to prove that the user logged in (TBD)
     * on fail return some sort of failure.
     */
    @PostMapping("/login")
    @AllowCORS
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body)
    {
        var response = userService.attemptLogin(body);
        if (response == null)
        {
            return ResponseEntity.status(403).build();
        }
        else
        {
            return ResponseEntity.ok(response);
        }
    }

    /**
     * RegisterUser takes a RegisterRequest and persists a UserEntity to the Database
     * returns a status code of 400 when either the username or password are invalid
     * returns a status code of 409 when a UserEntity with the same Username already exists in the database
     */
    @PutMapping("/login")
    @AllowCORS
    public ResponseEntity<RegisterRequest> registerUser(@RequestBody RegisterRequest body)
    {
        try {
            var response = userService.registerNewUser(body);
            return ResponseEntity.ok(response);
        }
        catch (InvalidCredentialsException e) {
            return ResponseEntity.status(400).build();
        }
        catch (DatabaseConflictException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @Autowired
    public LoginController(UserService userService) { this.userService = userService; }
}
