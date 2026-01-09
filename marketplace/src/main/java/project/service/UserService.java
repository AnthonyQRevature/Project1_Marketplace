package project.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import project.Repository.Entities.UserEntity;
import project.Repository.dao.UserDao;
import project.controller.model.UserModel;
import project.util.DateUtil;
import project.util.Hasher;

/*
 * a service class bean
 */
@Service
public class UserService {

    UserDao dao;
    Hasher hasher;
    DateUtil dateUtil;

    /*
     * a response entity represents an HTML response
     * its kind of like an optional.
     * Im not so sure about what is needed of the models. whatever structure we give it,
     * it has to be serializable somehow.
     * The example that I have access to uses json serialization to send information between the
     * client and server.
     */
    /*
     * 
     */
    public ResponseEntity<UserModel> registerNewUser(UserModel user)
    {
        UserEntity entity = new UserEntity();

        //TODO check password requirements, email existence, etc.
        if (user.getPassword().length() < 8)
        {
            return ResponseEntity.badRequest().build();
        }

        //check existence
        if (dao.findUserByUsername(user.getUsername()) != null)
        {
            //already in db
            return ResponseEntity.status(409).build();
        }
        else
        {//potentially factor conversions into a seperate method

            //conversion from model to entity
            //dao.save will return an entity, guarenteed nonnull
            //this entity will have it's ID field filled in unlike the one that is passed into the function
            entity.setUsername(user.getUsername());
            entity.setEmail(user.getEmail());

            //assign the password field in the entity
            String hash = hasher.hashPassword(user.getPassword());
            entity.setPasswordHash(hash);

            UserEntity result = dao.save(entity);

            //conversion from entity to model
            UserModel ret = new UserModel(result.getEmail(), null, result.getUsername());
            return ResponseEntity.ok(ret);
        }
        //return ResponseEntity.status(400).build();
    }

    //achieves constructor injection
    @Autowired
    public UserService(UserDao dao, Hasher hasher, DateUtil dateUtil) 
    {
        this.dao = dao;
        this.hasher = hasher;
        this.dateUtil = dateUtil;
    }
}
