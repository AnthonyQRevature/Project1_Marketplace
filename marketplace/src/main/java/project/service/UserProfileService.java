package project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserProfileDao;
import project.controller.model.UserProfileModel;

@Service
public class UserProfileService{
    UserProfileDao dao;

    public Optional<UserProfileModel> getProfileModelByUserID(int userID){
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(dao.findUserProfileByUserID(userID)));
        return ret;
    }

    //Worried about this function, feels like I should rewrite it.
    //Maybe make it so you can partially update a Profile?
    public Optional<UserProfileModel> updateUserProfile(UserProfileModel model) throws NoSuchFieldException{
        if (uniqueUserID(model.getUserID())){
            throw new NoSuchFieldException("User Profile does not exist.");
        }
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    public UserProfileEntity modelToEntity(UserProfileModel model){
        UserProfileEntity entity = new UserProfileEntity();

		entity.setUserID(model.getUserID());
		entity.setPfpUrl(model.getPfpUrl());
		entity.setBio(model.getBio());
		entity.setLatitude(model.getLatitude());
		entity.setLongitude(model.getLongitude());
		entity.setAddress(model.getAddress());

        return entity;
    }

    public UserProfileModel entityToModel(UserProfileEntity entity){
        UserProfileModel ret = new UserProfileModel(entity.getUserID(), entity.getPfpUrl(), entity.getBio(), entity.getLatitude(), entity.getLongitude(), entity.getAddress());
        return ret;
    }

    public boolean deleteUserProfileByID(int id){
        dao.deleteById(id);
        return dao.findUserProfileById(id) == null;
    }

    public boolean deleteUserProfileByUserID(int userID){
        dao.deleteByUserID(userID);
        return dao.findUserProfileByUserID(userID) == null;
    }

    public boolean deleteUserProfileByModel(UserProfileModel model){
        return deleteUserProfileByUserID((model.getUserID()));
    }

    public boolean deleteUserProfileByEntity(UserProfileEntity entity){
        return deleteUserProfileByID(entity.getProfileId());
    }

    //Worried about this function, feels like I should rewrite it.
    public Optional<UserProfileModel> createNewUserProfile(UserProfileModel model) throws NoSuchFieldException{
        if(uniqueUserID(model.getUserID()) != true){
            throw new NoSuchFieldException("User Profile already exists.");
        }
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    public boolean uniqueUserID(int userID){
        return dao.findUserProfileByUserID(userID) == null;
    }
}