
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import project.Repository.Entities.UserEntity;
import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserDao;
import project.Repository.dao.UserProfileDao;
import project.controller.request.LoginRequest;
import project.controller.request.RegisterRequest;
import project.controller.response.LoginResponse;
import project.service.UserService;
import project.util.DefaultPfp;
import project.util.Hasher;
import project.util.TokenUtil;
import project.util.exception.DatabaseConflictException;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserDao dao;
    @Mock UserProfileDao profileDao;
    @Mock Hasher hasher;
    @Mock TokenUtil tokenUtil;
    @Mock DefaultPfp defaultPfp;
    @InjectMocks UserService userService;

    @AfterEach
    public void Reset()
    {
        //clean
        Mockito.reset(dao);
    }
    ////Tests of the UserProfileService Class
    //Get by id tests

    ////A basic test of the UserService Class
    //Retrieve by id tests
    @Test
    public void RetrieveByIDTest(){
    }

    //Attempt login tests
    @Test
    public void AttemptLoginTest(){
    }

    //Registering users tests
    @Test
    public void CreateUserTest() throws Exception
    {
        //arrange
        
        RegisterRequest input = new RegisterRequest("test_email", "password", "username");

        UserEntity expectedSave = new UserEntity();
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password"); //validate the hash of the password
        expectedSave.setEmail("test_email");
        expectedSave.setRole(UserEntity.UserRole.user);

        UserEntity daoResponse = new UserEntity(expectedSave);
        daoResponse.setId(1);
        RegisterRequest expectedResponse = new RegisterRequest("test_email", null, "username");

        UserProfileEntity profileSave = new UserProfileEntity();
        profileSave.setUserID(1);
        profileSave.setPfpEncoded("default pfp");

        when(dao.findUserByUsername("username")).thenReturn(null);
        when(dao.save(expectedSave)).thenReturn(daoResponse);
        when(hasher.hashPassword("password")).thenReturn("hashed_password");

        //user profile
        when(defaultPfp.get()).thenReturn("default pfp");

        //act
        var ret = userService.registerNewUser(input);

        //assert
        assertEquals(expectedResponse, ret);
        verify(dao, times(1)).findUserByUsername("username");
        verify(dao, times(1)).save(expectedSave);
        verify(profileDao, times(1)).save(profileSave);
    }

    //a checking that users can not share usernames
    @Test
    public void noDuplicatesUserTest() throws Exception {
        //I need to figure out how to do this without just registering the same user twice.
        //Ideally the first user should already be registered
        UserEntity expectedSave = new UserEntity();
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password"); //validate the hash of the password
        expectedSave.setEmail("test_email");

        UserEntity daoResponse = new UserEntity(expectedSave);
        when(dao.findUserByUsername("username")).thenReturn(daoResponse);
        RegisterRequest input = new RegisterRequest("test_email", "password", "username");
        
        try {
            userService.registerNewUser(input);
            assert(false);
        } catch (DatabaseConflictException e) {
        }
    }

    @Test
    public void loginTest(){
        UserEntity userAcct = new UserEntity();
        userAcct.setId(1);
        userAcct.setUsername("username");
        userAcct.setPasswordHash("hashed_password");
        userAcct.setEmail("test_email");
        userAcct.setRole(UserEntity.UserRole.user);

        when(dao.findUserByUsername("username")).thenReturn(userAcct);
        when(hasher.verifyPassword("hashed_password", "password")).thenReturn(true);
        when(tokenUtil.makeToken("username", 1)).thenReturn("token");

        LoginResponse log = userService.attemptLogin(new LoginRequest("username", "password"));
        
        assertEquals("token", log.getEncryptedToken());
        assertEquals(UserEntity.UserRole.user.value, log.getRole());
    }

    /*
    @Test
    public void FailTest()
    {
        assertTrue(false);
    }
    */
}