
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import project.service.UserService;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserDao dao;
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
        UserEntity input = new UserEntity();
        input.setUsername("username");
        input.setEmail("email");

        UserEntity expectedSave = new UserEntity(input);
        expectedSave.setPasswordHash("Password"); //validate the hash of the password

        UserEntity response = new UserEntity(expectedSave);
        response.setUserId(1);

        when(dao.findUserByUsername("username")).thenReturn(null);
        when(dao.save(expectedSave)).thenReturn(response);

        //act
        var ret = userService.RegisterNewUser(inputPassword, input);

        //assert
        assertEquals(response, ret.getBody());
        verify(dao, times(1)).findUserByUsername("username");
        verify(dao, times(1)).save(expectedSave);
    }

    @Test
    public void FailTest()
    {
        assertTrue(false);
    }
}
