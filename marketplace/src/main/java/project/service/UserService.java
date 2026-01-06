package project.service;

import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import project.Repository.Entities.UserEntity;
import project.Repository.dao.UserDao;
import project.service.model.UserModel;

/*
 * a service class bean
 */
@Service
public class UserService {

    UserDao dao;

    /*
     * a response entity represents an HTML response
     * its kind of like an optional.
     * Im not so sure about what is needed of the models. whatever structure we give it,
     * it has to be serializable somehow.
     * The example that I have access to uses json serialization to send information between the
     * client and server.
     */
    /*
     * user is an object that has fields assigned to it except for maybe the ID field and the password field
     */
    public ResponseEntity<UserEntity> RegisterNewUser(String password, UserEntity user)
    {
        UnaryOperator<String> hasher = (String ps) -> ps;

        //check password requirements, email existence, etc.


        //assign the password field in the entity
        user.setPasswordHash(hasher.apply(password));

        //check existence
        if (dao.findUserByUsername(user.getUsername()) != null)
        {
            //already in db
            return ResponseEntity.status(409).build();
        }
        else
        {
            //dao.save will return an entity, guarenteed nonnull
            //this entity will have it's ID field filled in unlike the one that is passed into the function
            var result = dao.save(user);
            return ResponseEntity.ok(result);
        }
        //return ResponseEntity.status(400).build();
    }

    //achieves constructor injection
    @Autowired
    public UserService(UserDao dao) {this.dao = dao;}
}
