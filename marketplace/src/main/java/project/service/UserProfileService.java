package project.service;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserProfileDao;
import project.controller.model.UserProfileModel;
import project.util.exception.DatabaseConflictException;

@Service
public class UserProfileService{
    UserProfileDao dao;

    //TODO
    public UserProfileEntity addMedia(MultipartFile file, Integer user_id)
    {
        throw new RuntimeException("not yet implemented");
    }

    public Optional<UserProfileEntity> getProfileEntityByUserID(int userID){
        Optional<UserProfileEntity> ret = Optional.ofNullable(dao.findUserProfileByUserID(userID));
        return ret;
    }

    //Worried about this function, feels like I should rewrite it.
    //Maybe make it so you can partially update a Profile?
    public Optional<UserProfileModel> updateUserProfile(UserProfileModel model) throws AccountNotFoundException{
        if (uniqueUserID(model.getUserID())){
            throw new AccountNotFoundException("User Profile does not exist.");
        }
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    public UserProfileEntity modelToEntity(UserProfileModel model) {
        if (uniqueUserID(model.getUserID())){
            UserProfileEntity entity = new UserProfileEntity();
            entity.setUserID(model.getUserID());
            entity.setPfp_url(model.getPfpUrl());
            entity.setBio(model.getBio());
            entity.setLatitude(model.getLatitude());
            entity.setLongitude(model.getLongitude());
            entity.setAddress(model.getAddress());
            return entity;
        }
        UserProfileEntity entity = getProfileEntityByUserID(model.getUserID()).get();
        return entity;
    }

    public UserProfileModel entityToModel(UserProfileEntity entity){
        UserProfileModel ret = new UserProfileModel(entity.getUserID(), entity.getPfp_url(), entity.getBio(), entity.getLatitude(), entity.getLongitude(), entity.getAddress());
        return ret;
    }

    public boolean deleteUserProfileById(int id){
        dao.deleteById(id);
        return dao.getReferenceById(id) == null;
    }

    public boolean deleteUserProfileByUserID(int userID){
        dao.deleteByUserID(userID);
        return dao.findUserProfileByUserID(userID) == null;
    }

    public boolean deleteUserProfileByModel(UserProfileModel model){
        return deleteUserProfileByUserID((model.getUserID()));
    }

    public boolean deleteUserProfileByEntity(UserProfileEntity entity){
        return deleteUserProfileById(entity.getId());
    }

    //Worried about this function, feels like I should rewrite it.
    public Optional<UserProfileModel> createNewUserProfile(UserProfileModel model) throws DatabaseConflictException{
        if(uniqueUserID(model.getUserID()) != true){
            throw new DatabaseConflictException();
        }
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    public boolean uniqueUserID(int userID){
        return dao.findUserProfileByUserID(userID) == null;
    }
}
