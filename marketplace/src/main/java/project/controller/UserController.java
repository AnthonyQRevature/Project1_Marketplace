package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.UserEntity;
import project.Repository.Entities.UserProfileEntity;
import project.controller.model.UserProfileModel;
import project.controller.response.UserResponse;
import project.controller.response.ProfileResponse;
import project.service.UserProfileService;
import project.service.UserService;
import project.util.AllowCORS;

@RestController
@AllowCORS
public class UserController {
    UserService userService;
    UserProfileService userProfileService;

    @Autowired
    public UserController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/users/by-username/{username}")
    public ResponseEntity<UserResponse> getUserAndProfileByUsername(@PathVariable("username") String username) {
        try{
            return ResponseEntity.ok(userService.retrieveByUsername(username).get());
        } catch (Exception e){
            return ResponseEntity.badRequest().header("cause", e.getMessage()).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserAndProfileById(@PathVariable("user_id") int user_id) {
        try{
            UserEntity entity = userService.retrieveByID(user_id).get();
            UserProfileEntity profileEntity = userProfileService.getProfileEntityByUserID(user_id).get();
            ProfileResponse profileResponse = new ProfileResponse(profileEntity.getBio(), profileEntity.getLatitude(), profileEntity.getLongitude(), profileEntity.getPfp_url());
            UserResponse response = new UserResponse(entity.getEmail(), user_id, profileResponse, entity.getUsername(), entity.getVerifiedSeller());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().header("cause", e.getMessage()).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> patchUserAndProfile(@RequestBody UserProfileModel body) {
        try{
            return ResponseEntity.ok(userProfileService.modelToEntity(userProfileService.updateUserProfile(body).get()));
        } catch (Exception e){
            return ResponseEntity.badRequest().header("cause", e.getMessage()).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Integer> deleteUserAndProfile(@PathVariable("id") Integer id) {
        try{
            userProfileService.deleteUserProfileById(id);
            userService.deleteUserById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().header("cause", e.getMessage()).build();
        }
    }
}
