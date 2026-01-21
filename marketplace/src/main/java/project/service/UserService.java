package project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.UserEntity;
import project.Repository.dao.UserDao;
import project.controller.request.LoginRequest;
import project.controller.request.RegisterRequest;
import project.controller.response.LoginResponse;
import project.util.DateUtil;
import project.util.Hasher;
import project.util.TokenUtil;
import project.util.exception.DatabaseConflictException;
import project.util.exception.InvalidCredentialsException;

/*
 * a service class bean
 */
@Service
public class UserService {

    UserDao dao;
    Hasher hasher;
    DateUtil dateUtil;
    TokenUtil tokenUtil;

    /*
     * a response entity represents an HTML response
     * its kind of like an optional.
     * Im not so sure about what is needed of the models. whatever structure we give it,
     * it has to be serializable somehow.
     * The example that I have access to uses json serialization to send information between the
     * client and server.
     */
    /**
     * 
     */
    public RegisterRequest registerNewUser(RegisterRequest user) throws 
        InvalidCredentialsException,
        DatabaseConflictException
    {
        UserEntity entity = new UserEntity();

        //TODO check password requirements, email existence, etc.
        if (user.getPassword().length() < 8)
        {
            throw new InvalidCredentialsException();
            //return ResponseEntity.badRequest().build();
        }

        //check existence
        if (dao.findUserByUsername(user.getUsername()) != null)
        {
            //already in db
            throw new DatabaseConflictException();
            //return ResponseEntity.status(409).build();
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
            RegisterRequest ret = new RegisterRequest(result.getEmail(), null, result.getUsername());
            return ret;
        }
        //return ResponseEntity.status(400).build();
    }

    /**
     * Takes a login request and verifys it then returns an object 
     * representing either a login success or failiure
     * @param request
     * @return LoginResponse if request corresponds to a valid user, null otherwise
     */
    public LoginResponse attemptLogin(LoginRequest request) /* throws AuthenticationException */ {
        UserEntity logAttempt = dao.findUserByUsername(request.getUsername());
        
        System.out.printf("Recieved: %s\n", request.toString());
        System.out.printf("password comaprison: \n%s\n%s", hasher.hashPassword(request.getPassword()), logAttempt.getPasswordHash());

        if(hasher.verifyPassword(logAttempt.getPasswordHash(), request.getPassword())){
            String token = tokenUtil.makeToken(logAttempt.getUsername(), logAttempt.getId());
            LoginResponse response = new LoginResponse(logAttempt.getId(), logAttempt.getUsername(), token);
            return response;
        }
        else{
            return null;
        }
    }

    public Optional<UserEntity> retrieveByUsername(String username){
        return Optional.ofNullable(dao.findUserByUsername(username));
    }

    public Optional<UserEntity> retrieveByID(int id){
        return Optional.ofNullable(dao.findUserById(id));
    }

    public boolean deleteUserById(int id){
        dao.deleteById(id);
        return dao.getReferenceById(id) == null;
    }

    //achieves constructor injection
    @Autowired
    public UserService(UserDao dao, Hasher hasher, DateUtil dateUtil, TokenUtil tokenUtil) 
    {
        this.dao = dao;
        this.hasher = hasher;
        this.dateUtil = dateUtil;
        this.tokenUtil = tokenUtil;
    }
}
