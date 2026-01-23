import javax.security.auth.login.AccountNotFoundException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserProfileDao;
import project.controller.model.UserProfileModel;
import project.controller.request.ProfileRequest;
import project.service.UserProfileService;
import project.util.Hasher;
import project.util.exception.DatabaseConflictException;


@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @Mock UserProfileDao dao;
    @Mock Hasher hasher;
    @InjectMocks UserProfileService service;

    @AfterEach
    public void Reset()
    {
        //clean
        Mockito.reset(dao);
    }

    //getProfileModelByUser_id
    @Test
    public void getModelByIDTest(){
        UserProfileEntity entity = new UserProfileEntity();

        entity.setUserID(1);
		entity.setPfpEncoded("aaa");
		entity.setBio("bbb");
		entity.setLatitude(2.0);
		entity.setLongitude(3.0);
		entity.setAddress("ccc");

        when(dao.findUserProfileByUserID(1)).thenReturn(entity);

        UserProfileEntity entity2 = (service.getProfileEntityByUserID(1).get());

        assertEquals(entity, entity2);
    }

    //updateUserProfile
    @Test
    public void UpdateProfileTest(){
        try {
            //I have no idea how to accurately test this.
            ProfileRequest profileRequest = new ProfileRequest("bb", 2.0, 3.0, "aa");
            UserProfileEntity entity = new UserProfileEntity();
            
            entity.setUserID(1);
            entity.setPfpEncoded("aaa");
            entity.setBio("bbb");
            entity.setLatitude(2.0);
            entity.setLongitude(3.0);
            entity.setAddress("ccc");
            
            when(dao.findUserProfileByUserID(1)).thenReturn(entity);
            
            UserProfileEntity entity2 = entity;
            entity.setUserID(1);
            entity.setPfpEncoded("aa");
            entity.setBio("bb");
            entity.setLatitude(2.0);
            entity.setLongitude(3.0);
            entity.setAddress("ccc");
            
            when(dao.save(entity2)).thenReturn(entity2);
            
            UserProfileModel model2 = (service.updateUserProfile(1, profileRequest).get());
            
            assertEquals(model2.getUser_id(), 1);
            assertEquals(model2.getPfp_encoded(), profileRequest.getPfp_encoded());
            assertEquals(model2.getBio(), profileRequest.getBio());
            assertEquals(model2.getLatitude(), profileRequest.getLatitude());
            assertEquals(model2.getLongitude(), profileRequest.getLongitude());
            assertEquals(model2.getAddress(), "ccc");
        } catch (Exception ex) {
            fail("There was an Exception.");
        }
    }
    @Test
    public void FailedProfileUpdateTest(){
        when(dao.findUserProfileByUserID(1)).thenReturn(null);
        
        ProfileRequest profileRequest = new ProfileRequest("bb", 2.0, 3.0, "aa");

        assertThrows(AccountNotFoundException.class, () -> {service.updateUserProfile(1, profileRequest);}, "User Profile does not exist.");
    }

    //modelToEntity
    @Test
    public void EntityConversionTest(){
        UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2.0, 3.0, "cc");

        UserProfileEntity entity = service.modelToEntity(model);

        assertEquals(entity.getUserID(), model.getUser_id());
        assertEquals(entity.getPfpEncoded(), model.getPfp_encoded());
        assertEquals(entity.getBio(), model.getBio());
        assertEquals(entity.getLatitude(), model.getLatitude());
        assertEquals(entity.getLongitude(), model.getLongitude());
        assertEquals(entity.getAddress(), model.getAddress());
    }

    //entityToModel
    @Test
    public void ModelConversionTest(){
        UserProfileEntity entity = new UserProfileEntity();

        entity.setUserID(1);
		entity.setPfpEncoded("aaa");
		entity.setBio("bbb");
		entity.setLatitude(2.0);
		entity.setLongitude(3.0);
		entity.setAddress("ccc");

        UserProfileModel model = service.entityToModel(entity);

        assertEquals(entity.getUserID(), model.getUser_id());
        assertEquals(entity.getPfpEncoded(), model.getPfp_encoded());
        assertEquals(entity.getBio(), model.getBio());
        assertEquals(entity.getLatitude(), model.getLatitude());
        assertEquals(entity.getLongitude(), model.getLongitude());
        assertEquals(entity.getAddress(), model.getAddress());
    }

    //deleteUserProfileByID
    @Test
    public void DeleteById(){
        when(dao.getReferenceById(1)).thenReturn(null);
        assertTrue(service.deleteUserProfileById(1));
    }
    @Test
    public void DeleteByIdFailure(){
        UserProfileEntity result = new UserProfileEntity();
        when(dao.getReferenceById(1)).thenReturn(result);
        assertFalse(service.deleteUserProfileById(1));
    }

    //deleteUserProfileByUser_id
    @Test
    public void DeleteByUser_id(){
        when(dao.getReferenceById(1)).thenReturn(null);
        assertTrue(service.deleteUserProfileByUserID(1));
    }
    @Test
    public void DeleteByUser_idFailure(){
        UserProfileEntity result = new UserProfileEntity();
        when(dao.findUserProfileByUserID(1)).thenReturn(result);
        assertFalse(service.deleteUserProfileByUserID(1));
    }

    //createNewUserProfile
    @Test
    public void CreateProfileTest(){
        try {
            //I have no idea how to accurately test this.
            UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2.0, 3.0, "cc");
            UserProfileEntity entity = new UserProfileEntity();
            
            entity.setUserID(1);
            entity.setPfpEncoded("aa");
            entity.setBio("bb");
            entity.setLatitude(2.0);
            entity.setLongitude(3.0);
            entity.setAddress("cc");
            
            when(dao.findUserProfileByUserID(1)).thenReturn(null);
            
            when(dao.save(entity)).thenReturn(entity);
            
            UserProfileModel model2 = (service.createNewUserProfile(model).get());
            
            assertEquals(model2.getUser_id(), model.getUser_id());
            assertEquals(model2.getPfp_encoded(), model.getPfp_encoded());
            assertEquals(model2.getBio(), model.getBio());
            assertEquals(model2.getLatitude(), model.getLatitude());
            assertEquals(model2.getLongitude(), model.getLongitude());
            assertEquals(model2.getAddress(), model.getAddress());
        } catch (Exception ex) {
            fail("There was an Exception.");
        }
    }
    @Test
    public void FailedProfileCreationTest(){
        UserProfileEntity entity = new UserProfileEntity();
        when(dao.findUserProfileByUserID(1)).thenReturn(entity);

        UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2.0, 3.0, "cc");

        assertThrows(DatabaseConflictException.class, () -> {service.createNewUserProfile(model);}, "User Profile already exists.");
    }

    //uniqueUser_id
    @Test
    public void UniqueIDTest(){
        when(dao.findUserProfileByUserID(1)).thenReturn(null);
        assertTrue(service.uniqueUser_id(1));
    }
    @Test
    public void ExistingIDTest(){
        UserProfileEntity result = new UserProfileEntity();
        when(dao.findUserProfileByUserID(1)).thenReturn(result);
        assertFalse(service.uniqueUser_id(1));
    }

    //TODO: add in the missing methods
}