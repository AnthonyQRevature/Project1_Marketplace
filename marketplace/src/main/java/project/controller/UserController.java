package project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import project.Repository.Entities.UserEntity;
import project.Repository.Entities.UserEntity.UserRole;
import project.controller.request.UserUpdateRequest;
import project.controller.response.ProfileResponse;
import project.controller.response.UserResponse;
import project.service.UserService;
import project.util.AllowCORS;
import project.util.Secure;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;

@RestController
@AllowCORS
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getUserAndProfileByUsername(@PathVariable("username") String username) {
        try{
            UserEntity entity = userService.retrieveByUsername(username).get();
            UserResponse response = toUserResponse(entity);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAndProfileById(@PathVariable("id") int id) {
        try{
            UserEntity entity = userService.retrieveByID(id).get();
            UserResponse response = toUserResponse(entity);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PatchMapping("/{id}")
    @Transactional
    @Secure
    public ResponseEntity<?> patchUserAndProfile(
        @RequestHeader("Authorization") String auth, 
        @PathVariable("id") Integer id, 
        @RequestBody UserUpdateRequest body
    ) {
        try{
            UserEntity entity = userService.updateById(id, body);
            UserResponse response = toUserResponse(entity);
            return ResponseEntity.ok(response);
        } catch (AccountNotFoundException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    private UserResponse toUserResponse(UserEntity entity) {
        ProfileResponse profileResponse = new ProfileResponse(
            entity.getUserProfile().getBio(), 
            entity.getUserProfile().getLatitude(), 
            entity.getUserProfile().getLongitude(),
            entity.getUserProfile().getPfpEncoded()
        );
        UserResponse response = new UserResponse(
            entity.getEmail(), entity.getId(), 
            profileResponse,
            entity.getRole().value,
            entity.getUsername(), 
            entity.getVerifiedSeller()
        );
        return response;
    }

    @DeleteMapping("/{id}")
    @Secure
    public ResponseEntity<?> deleteUserAndProfile(@RequestHeader("Authorization") String auth, @PathVariable("id") Integer id) {
        try{
            userService.deleteUserById(id);//on delete cascade
            return ResponseEntity.ok(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("")
    @SecureIndescriminate(SecurityLevel.ADMIN)
    public ResponseEntity<List<UserResponse>> getUsers(@RequestHeader("Authorization") String auth)
    {
        List<UserResponse> ret = new ArrayList<>();
        var users = userService.getAllUsers();
        for (var user : users)
        {
            UserResponse element = toUserResponse(user);
            
            ret.add(element);
        }

        return ResponseEntity.ok(ret);
    }

    @PatchMapping("/{id}/perms")
    @SecureIndescriminate(SecurityLevel.SUPER_USER)
    public ResponseEntity<?> setPerms(
        @RequestHeader("Authorization") String auth,
        @PathVariable Integer id,
        @RequestBody Integer role
    ) {
        var optional = userService.getById(id);
        if (!optional.isPresent())
        {
            //not present
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var user = optional.get();
        user.setRole(UserRole.of(role));
        userService.update(user);
        return ResponseEntity.ok().build();
    }

    /*
    Not needed
    @GetMapping("/ADMIN")
    @SecureIndescriminate(SecurityLevel.ADMIN)
    public ResponseEntity<?> privilegeCheck(@RequestHeader("Authorization") String authHeader) {
        try{
            return ResponseEntity.ok().body("");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
    */
}
