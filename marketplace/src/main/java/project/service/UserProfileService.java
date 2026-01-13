package project.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import project.controller.model.UserProfileModel;

@Service
public class UserProfileService{
    int a;
    Optional<UserProfileModel> getProfileModelByUserID(int userID){
    }
    Optional<UserProfileModel> updateUserProfile(UserProfileModel model){
    }
    UserProfileEntity modelToEntity(UserProfileModel model){
    }
    UserProfileModel entityToModel(UserProfileEntity entity){
    }
    boolean deleteUserProfileByID(int id){
    }
    boolean deleteUserProfileByUserID(int userID){
    }
    boolean deleteUserProfileByModel(UserProfileModel model){
    }
    boolean deleteUserProfileByEntity(UserProfileEntity entity){
    }
    Optional<UserProfileModel> createNewUserProfile(UserProfileModel model){
    }
    boolean uniqueUserID(int userID){
    }
}