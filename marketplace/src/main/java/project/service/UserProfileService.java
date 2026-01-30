package project.service;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.stereotype.Service;

import project.Repository.Entities.UserProfileEntity;
import project.controller.model.UserProfileModel;
import project.controller.request.ProfileRequest;
import project.controller.request.UserUpdateRequest;
import project.util.exception.DatabaseConflictException;

@Service
@Deprecated
public class UserProfileService{
    //UserProfileDao dao;
    

    public UserProfileService()
    {
    }

    

    @Deprecated
    public Optional<UserProfileEntity> getProfileEntityByUserID(int user_id){
        /*Optional<UserProfileEntity> ret = Optional.ofNullable(dao.findUserProfileByUserID(user_id));
        return ret;*/
        return Optional.empty();
    }

    //Worried about this function, feels like I should rewrite it.
    //Maybe make it so you can partially update a Profile?
    @Deprecated
    public Optional<UserProfileEntity> updateUserProfile(Integer id, ProfileRequest profileRequest) throws AccountNotFoundException{
        /*if (uniqueUser_id(id)){
            throw new AccountNotFoundException("User Profile does not exist.");
        }
        UserProfileEntity entity = getProfileEntityByUserID(id).get();
        entity.setPfpEncoded(profileRequest.getPfp_encoded());
        entity.setBio(profileRequest.getBio());
        entity.setLatitude(profileRequest.getLatitude());
        entity.setLongitude(profileRequest.getLongitude());
        UserProfileEntity result = dao.save(entity);
        Optional<UserProfileEntity> ret = Optional.ofNullable(result);
        return ret;*/
        return Optional.empty();
    }
    @Deprecated
    public Optional<UserProfileModel> updateUserProfileByUserId(Integer userId, UserUpdateRequest.Profile profile) throws AccountNotFoundException
    {
        /*if (uniqueUser_id(userId)){
            throw new AccountNotFoundException("User Profile does not exist.");
        }
        UserProfileEntity entity = getProfileEntityByUserID(userId).get();
        entity.setBio(profile.getBio());
        entity.setLatitude(profile.getLatitude());
        entity.setLongitude(profile.getLongitude());
        UserProfileEntity result = dao.save(entity);
        //Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return Optional.empty();*/
        return Optional.empty();
    }

    /*public boolean deleteUserProfileById(int id){
        dao.deleteById(id);
        return dao.getReferenceById(id) == null;
    }

    public boolean deleteUserProfileByUserID(int user_id){
        dao.deleteByUserID(user_id);
        return dao.findUserProfileByUserID(user_id) == null;
    }

    public boolean deleteUserProfileByModel(UserProfileModel model){
        return deleteUserProfileByUserID((model.getUser_id()));
    }

    public boolean deleteUserProfileByEntity(UserProfileEntity entity){
        return deleteUserProfileById(entity.getId());
    }*/

    //Worried about this function, feels like I should rewrite it.
    @Deprecated
    public Optional<UserProfileModel> createNewUserProfile(UserProfileModel model) throws DatabaseConflictException{
        /*if(uniqueUser_id(model.getUser_id()) != true){
            throw new DatabaseConflictException();
        }*/
        //UserProfileEntity result = dao.save(modelToEntity(model));
        //Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return Optional.empty();
    }

    /*public boolean uniqueUser_id(Integer user_id){
        var val = dao.existsByUserID(user_id);
        return !val;
    }

    @Autowired
    public UserProfileService(UserProfileDao dao, FileEncoder encoder) {
        this.dao = dao;
    }*/
    
}
