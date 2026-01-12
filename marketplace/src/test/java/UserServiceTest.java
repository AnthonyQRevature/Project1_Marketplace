import java.sql.Date;

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
import project.controller.model.UserModel;
import project.service.UserService;
import project.util.DateUtil;
import project.util.Hasher;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserDao dao;
    @Mock DateUtil dateUtil;
    @Mock Hasher hasher;
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
    public void CreateUserTest()
    {
        //arrange
        /*
        UserEntity input = new UserEntity();
        input.setUsername("username");
        input.setEmail("email");
        */
        
        UserModel input = new UserModel("test_email", "password", "username");
        Date testDate = new Date(0);

        UserEntity expectedSave = new UserEntity();
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password"); //validate the hash of the password
        expectedSave.setEmail("test_email");

        UserEntity daoResponse = new UserEntity(expectedSave);
        daoResponse.setUserId(1);
        UserModel expectedResponse = new UserModel("test_email", null, "username");

        when(dao.findUserByUsername("username")).thenReturn(null);
        when(dao.save(expectedSave)).thenReturn(daoResponse);
        when(dateUtil.currentDate()).thenReturn(testDate);
        when(hasher.hashPassword("password")).thenReturn("hashed_password");

        //act
        var ret = userService.registerNewUser(input);

        //assert
        assertEquals(expectedResponse, ret.getBody());
        verify(dao, times(1)).findUserByUsername("username");
        verify(dao, times(1)).save(expectedSave);
    }

    //a checking that users can not share usernames
    @Test
    public void noDuplicatesUserTest(){
        //I need to figure out how to do this without just registering the same user twice.
        //Ideally the first user should already be registered
        UserEntity expectedSave = new UserEntity();
        expectedSave.setUsername("username");
        expectedSave.setPasswordHash("hashed_password"); //validate the hash of the password
        expectedSave.setEmail("test_email");

        UserEntity daoResponse = new UserEntity(expectedSave);
        when(dao.findUserByUsername("username")).thenReturn(daoResponse);
        UserModel input = new UserModel("test_email", "password", "username");
        var ret = userService.registerNewUser(input);
        assertEquals(HttpStatus.CONFLICT, ret.getStatusCode());
    }


    /*
    @Test
    public void FailTest()
    {
        assertTrue(false);
    }
    */
}
