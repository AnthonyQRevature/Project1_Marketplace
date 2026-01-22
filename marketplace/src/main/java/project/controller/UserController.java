package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.UserEntity;
import project.Repository.Entities.UserProfileEntity;
import project.controller.request.ProfileRequest;
import project.controller.request.UserUpdateRequest;
import project.controller.response.ProfileResponse;
import project.controller.response.UserResponse;
import project.service.UserProfileService;
import project.service.UserService;
import project.util.AllowCORS;

@RestController
@AllowCORS
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserProfileService userProfileService;

    @Autowired
    public UserController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getUserAndProfileByUsername(@PathVariable("username") String username) {
        try{
            UserEntity entity = userService.retrieveByUsername(username).get();
            UserProfileEntity profileEntity = userProfileService.getProfileEntityByUser_id(entity.getId()).get();

            ProfileResponse profileResponse = new ProfileResponse(profileEntity.getBio(), profileEntity.getLatitude(), profileEntity.getLongitude(), profileEntity.getPfp_encoded());
            UserResponse response = new UserResponse(entity.getEmail(), entity.getId(), profileResponse, username, entity.getVerifiedSeller());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAndProfileById(@PathVariable("id") int id) {
        try{
            UserEntity entity = userService.retrieveByID(id).get();
            UserProfileEntity profileEntity = userProfileService.getProfileEntityByUser_id(id).get();

            ProfileResponse profileResponse = new ProfileResponse(profileEntity.getBio(), profileEntity.getLatitude(), profileEntity.getLongitude(), profileEntity.getPfp_encoded());
            UserResponse response = new UserResponse(entity.getEmail(), id, profileResponse, entity.getUsername(), entity.getVerifiedSeller());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    //Fix this Murphy
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUserAndProfile(@PathVariable("id") Integer id, @RequestBody UserUpdateRequest body) {
        try{
            //Maybe add if statements so we do not do uneeded saving? But then we would be checking excessivly
            UserEntity entity = userService.updateUserEmail(id, body).get();
            ProfileRequest profileRequest = body.getProfileRequest();
            UserProfileEntity profileEntity = userProfileService.modelToEntity(userProfileService.updateUserProfile(id, profileRequest).get());

            ProfileResponse profileResponse = new ProfileResponse(profileEntity.getBio(), profileEntity.getLatitude(), profileEntity.getLongitude(), profileEntity.getPfp_encoded());
            UserResponse response = new UserResponse(entity.getEmail(), entity.getId(), profileResponse, entity.getUsername(), entity.getVerifiedSeller());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAndProfile(@PathVariable("id") Integer id) {
        try{
            userProfileService.deleteUserProfileById(id);
            userService.deleteUserById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
