package project.service;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import project.Repository.Entities.UserEntity;
import project.Repository.Entities.UserEntity.UserRole;
import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserDao;
import project.controller.request.LoginRequest;
import project.controller.request.RegisterRequest;
import project.controller.request.UserUpdateRequest;
import project.controller.response.LoginResponse;
import project.util.DefaultPfp;
import project.util.FileEncoder;
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
    DefaultPfp defaultPfp;
    Hasher hasher;
    TokenUtil tokenUtil;
    FileEncoder encoder;

    Rectangle targetDimensions;

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
    @Transactional
    public RegisterRequest registerNewUser(RegisterRequest user) throws 
        InvalidCredentialsException,
        DatabaseConflictException
    {
        UserEntity entity = new UserEntity();

        //TODO check password requirements, email existence, etc.
        if (user.getPassword().length() < 8)
        {
            throw new InvalidCredentialsException();
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
            entity.setRole(UserRole.user); //default value

            //assign the password field in the entity
            String hash = hasher.hashPassword(user.getPassword());
            entity.setPasswordHash(hash);
            
            //create a corresponding profile
            UserProfileEntity profileEntity = new UserProfileEntity();
            profileEntity.setUserEntity(entity);
            profileEntity.setPfpEncoded(defaultPfp.get());
            entity.setUserProfile(profileEntity);
            
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

        if (logAttempt == null)
        {
            return null;
        }

        if(hasher.verifyPassword(logAttempt.getPasswordHash(), request.getPassword())){
            String token = tokenUtil.makeToken(logAttempt.getUsername(), logAttempt.getId());
            LoginResponse response = new LoginResponse(logAttempt.getId(), logAttempt.getRole(), logAttempt.getUsername(), token);
            return response;
        }
        else{
            return null;
        }
    }

    public Optional<UserEntity> updateUserEmail(Integer id, UserUpdateRequest body) throws AccountNotFoundException {
        //check existence
        if (dao.findUserById(id) == null) {
            //not in db
            throw new AccountNotFoundException();
        } else {
            UserEntity entity = dao.findUserById(id);
            entity.setEmail(body.getEmail());

            UserEntity result = dao.save(entity);
            Optional<UserEntity> ret = Optional.ofNullable(result);
            return ret;
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
    public UserService(
        UserDao dao,
        Hasher hasher, 
        TokenUtil tokenUtil, 
        DefaultPfp defaultPfp,
        FileEncoder encoder
    ) {
        this.dao = dao;
        this.defaultPfp = defaultPfp;
        this.hasher = hasher;
        this.tokenUtil = tokenUtil;
        this.encoder = encoder;

        this.targetDimensions = new Rectangle(32, 32);
    }

    public List<UserEntity> getAllUsers() {
        return dao.findAll();
    }

    public Optional<UserEntity> getById(Integer id) {
        return dao.findById(id);
    }

    public UserEntity updateById(Integer id, UserUpdateRequest body) 
    throws AccountNotFoundException
    {
        var optional = dao.findById(id);
        if (!optional.isPresent())
        {
            throw new AccountNotFoundException();
        }
        UserEntity entity = optional.get();
        UserProfileEntity profileEntity = entity.getUserProfile();

        entity.setEmail(body.getEmail());
        profileEntity.setBio(body.getProfile().getBio());
        profileEntity.setLatitude(body.getProfile().getLatitude());
        profileEntity.setLongitude(body.getProfile().getLongitude());

        entity = dao.save(entity);
        return entity;
    }

    @Transactional
    public UserEntity addMedia(MultipartFile file, Integer user_id) throws IOException
    {
        String encodedFile;
        UserEntity entity = dao.findById(user_id).get();

        try (InputStream stream = file.getInputStream())
        {
            //removes the alpha channel
            BufferedImage resized = encoder.cropAndResize(ImageIO.read(stream), targetDimensions);
            encodedFile = encoder.base64Encode(resized);
            entity.getUserProfile().setPfpEncoded(encodedFile);
            entity = dao.save(entity);
            return entity;
        }
    }
}
