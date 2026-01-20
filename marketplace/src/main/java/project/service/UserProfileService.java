package project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserProfileDao;
import project.controller.model.UserProfileModel;

@Service
public class UserProfileService{
    UserProfileDao dao;

    Optional<UserProfileModel> getProfileModelByUserID(int userID){
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(dao.findUserProfileByUserID(userID)));
        return ret;
    }

    Optional<UserProfileModel> updateUserProfile(UserProfileModel model) throws NoSuchFieldException{
        if (dao.findUserProfileByUserID(model.getUserID()) != null){
            throw new NoSuchFieldException("User Profile does not exist.");
        }
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    UserProfileEntity modelToEntity(UserProfileModel model){
        UserProfileEntity entity = new UserProfileEntity();

		entity.setUserID(model.getUserID());
		entity.setPfpUrl(model.getBio());
		entity.setBio(model.getBio());
		entity.setLatitude(model.getLatitude());
		entity.setLongitude(model.getLongitude());
		entity.setAddress(model.getAddress());

        return entity;
    }

    UserProfileModel entityToModel(UserProfileEntity entity){
        UserProfileModel ret = new UserProfileModel(entity.getUserID(), entity.getPfpUrl(), entity.getBio(), entity.getLatitude(), entity.getLongitude(), entity.getAddress());
        return ret;
    }

    boolean deleteUserProfileByID(int id){
        dao.deleteById(id);
        return dao.findUserProfileById(id) == null;
    }

    boolean deleteUserProfileByUserID(int userID){
        dao.deleteByUserID(userID);
        return dao.findUserProfileByUserID(userID) == null;
    }

    boolean deleteUserProfileByModel(UserProfileModel model){
        return deleteUserProfileByUserID((model.getUserID()));
    }

    boolean deleteUserProfileByEntity(UserProfileEntity entity){
        return deleteUserProfileByID(entity.getProfileId());
    }

    Optional<UserProfileModel> createNewUserProfile(UserProfileModel model){
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    boolean uniqueUserID(int userID){
        return dao.findUserProfileByUserID(userID) != null;
    }
}