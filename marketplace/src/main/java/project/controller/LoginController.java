package project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.UserEntity;
import project.service.UserService;

/*
 * A Controller for the /login endpoint
 */
@RestController
public class LoginController {

    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserEntity> Login()
    {
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/login")
    public ResponseEntity<UserEntity> RegisterUser()
    {
        return ResponseEntity.internalServerError().build();
    }

    public LoginController(UserService userService) { this.userService = userService; }
}
