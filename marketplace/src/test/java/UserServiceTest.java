
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
import org.springframework.http.HttpStatus;

import project.Repository.Entities.UserEntity;
import project.Repository.dao.UserDao;
import project.controller.model.LoginModel;
import project.controller.request.LoginRequest;
import project.controller.request.RegisterRequest;
import project.controller.response.LoginResponse;
import project.service.UserService;
import project.util.DateUtil;
import project.util.Hasher;
import project.util.TokenUtil;
import project.util.exception.DatabaseConflictException;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserDao dao;
    @Mock DateUtil dateUtil;
    @Mock Hasher hasher;
    @Mock TokenUtil tokenUtil;
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
        String inputPassword = "Password";

        /*
        UserEntity input = new UserEntity();
        input.setUsername("username");
        input.setEmail("email");
        */
        
        RegisterRequest input = new RegisterRequest("test_email", "password", "username");

        UserEntity expectedSave = new UserEntity();
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password"); //validate the hash of the password
        expectedSave.setEmail("test_email");

        UserEntity daoResponse = new UserEntity(expectedSave);
        daoResponse.setId(1);
        RegisterRequest expectedResponse = new RegisterRequest("test_email", null, "username");

        when(dao.findUserByUsername("username")).thenReturn(null);
        when(dao.save(expectedSave)).thenReturn(daoResponse);
        when(hasher.hashPassword("password")).thenReturn("hashed_password");

        //act
        var ret = userService.registerNewUser(input);

        //assert
        assertEquals(expectedResponse, ret);
        verify(dao, times(1)).findUserByUsername("username");
        verify(dao, times(1)).save(expectedSave);
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
            var ret = userService.registerNewUser(input);
            assert(false);
        } catch (DatabaseConflictException e) {
            return;
        }
    }

    @Test
    public void loginTest(){
        UserEntity expectedSave = new UserEntity();
        expectedSave.setId(1);
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password");
        expectedSave.setEmail("test_email");

        UserEntity daoResponse = new UserEntity(expectedSave);
        when(dao.findUserByUsername("username")).thenReturn(daoResponse);
        when(hasher.verifyPassword("hashed_password", "password")).thenReturn(true);
        when(tokenUtil.makeToken("username", 1)).thenReturn("token");

        LoginResponse log = userService.attemptLogin(new LoginRequest("username", "password"));
        
        assertEquals("token", log.getEncryptedToken());
    }

    /*
    @Test
    public void FailTest()
    {
        assertTrue(false);
    }
    */
}