
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
import project.Repository.dao.UserDao;
import project.controller.model.UserModel;
import project.service.UserService;
import project.util.Hasher;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserDao dao;
    @Mock Hasher hasher;
    @InjectMocks UserService userService;

    @AfterEach
    public void Reset()
    {
        //clean
        Mockito.reset(dao);
    }

    //a basic test of the UserServiceClass
    @Test
    public void CreateUserTest()
    {
        //arrange
        String inputPassword = "Password";

        /*
        UserEntity input = new UserEntity();
        input.setUsername("username");
        input.setEmail("email");
        */
        
        UserModel input = new UserModel("test_email", "password", "username");

        UserEntity expectedSave = new UserEntity();
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password"); //validate the hash of the password
        expectedSave.setEmail("test_email");

        UserEntity daoResponse = new UserEntity(expectedSave);
        daoResponse.setUserId(1);
        UserModel expectedResponse = new UserModel("test_email", null, "username");

        when(dao.findUserByUsername("username")).thenReturn(null);
        when(dao.save(expectedSave)).thenReturn(daoResponse);
        when(hasher.hashPassword("password")).thenReturn("hashed_password");

        //act
        var ret = userService.registerNewUser(input);

        //assert
        assertEquals(expectedResponse, ret.getBody());
        verify(dao, times(1)).findUserByUsername("username");
        verify(dao, times(1)).save(expectedSave);
    }

    /*
    @Test
    public void FailTest()
    {
        assertTrue(false);
    }
    */
}
