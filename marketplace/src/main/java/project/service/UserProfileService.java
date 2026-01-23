package project.service;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserProfileDao;
import project.controller.model.UserProfileModel;
import project.controller.request.ProfileRequest;
import project.util.FileEncoder;
import project.util.exception.DatabaseConflictException;

@Service
public class UserProfileService{
    @Autowired
    UserProfileDao dao;
    @Autowired
    FileEncoder encoder;
    Rectangle targetDimensions;

    public UserProfileService()
    {
        this.targetDimensions = new Rectangle(32, 32);
    }

    @Transactional
    public UserProfileEntity addMedia(MultipartFile file, Integer user_id) throws IOException
    {
        String encodedFile;
        UserProfileEntity entity = dao.findUserProfileByUserID(user_id);

        try (InputStream stream = file.getInputStream())
        {
            //removes the alpha channel
            BufferedImage resized = encoder.cropAndResize(ImageIO.read(stream), targetDimensions);
            encodedFile = encoder.base64Encode(resized);
            entity.setPfpEncoded(encodedFile);
            entity = dao.save(entity);
            return entity;
        }
    }

    public Optional<UserProfileEntity> getProfileEntityByUserID(int user_id){
        Optional<UserProfileEntity> ret = Optional.ofNullable(dao.findUserProfileByUserID(user_id));
        return ret;
    }

    //Worried about this function, feels like I should rewrite it.
    //Maybe make it so you can partially update a Profile?
    public Optional<UserProfileModel> updateUserProfile(Integer id, ProfileRequest profileRequest) throws AccountNotFoundException{
        if (uniqueUser_id(id)){
            throw new AccountNotFoundException("User Profile does not exist.");
        }
        UserProfileEntity entity = getProfileEntityByUserID(id).get();
        entity.setPfpEncoded(profileRequest.getPfp_encoded());
        entity.setBio(profileRequest.getBio());
        entity.setLatitude(profileRequest.getLatitude());
        entity.setLongitude(profileRequest.getLongitude());
        UserProfileEntity result = dao.save(entity);
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    public UserProfileEntity modelToEntity(UserProfileModel model) {
        if (uniqueUser_id(model.getUser_id())){
            UserProfileEntity entity = new UserProfileEntity();
            entity.setUserID(model.getUser_id());
            entity.setPfpEncoded(model.getPfp_encoded());
            entity.setBio(model.getBio());
            entity.setLatitude(model.getLatitude());
            entity.setLongitude(model.getLongitude());
            entity.setAddress(model.getAddress());
            return entity;
        }
        UserProfileEntity entity = getProfileEntityByUserID(model.getUser_id()).get();
        return entity;
    }

    public UserProfileModel entityToModel(UserProfileEntity entity){
        UserProfileModel ret = new UserProfileModel(entity.getUserID(), entity.getPfpEncoded(), entity.getBio(), entity.getLatitude(), entity.getLongitude(), entity.getAddress());
        return ret;
    }

    public boolean deleteUserProfileById(int id){
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
    }

    //Worried about this function, feels like I should rewrite it.
    public Optional<UserProfileModel> createNewUserProfile(UserProfileModel model) throws DatabaseConflictException{
        if(uniqueUser_id(model.getUser_id()) != true){
            throw new DatabaseConflictException();
        }
        UserProfileEntity result = dao.save(modelToEntity(model));
        Optional<UserProfileModel> ret = Optional.ofNullable(entityToModel(result));
        return ret;
    }

    public boolean uniqueUser_id(int user_id){
        return dao.findUserProfileByUserID(user_id) == null;
    }

    
}
