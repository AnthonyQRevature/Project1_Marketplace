
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
import project.service.UserProfileService;
import project.util.Hasher;


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

    //getProfileModelByUserID
    @Test
    public void getModelByIDTest(){
        UserProfileEntity entity = new UserProfileEntity();

        entity.setUserID(1);
		entity.setPfpUrl("aaa");
		entity.setBio("bbb");
		entity.setLatitude(2);
		entity.setLongitude(3);
		entity.setAddress("ccc");

        when(dao.findUserProfileByUserID(1)).thenReturn(entity);

        UserProfileModel model = (service.getProfileModelByUserID(1).get());

        assertEquals(service.entityToModel(entity), model);
    }

    //updateUserProfile
    @Test
    public void UpdateProfileTest(){
        try {
            //I have no idea how to accurately test this.
            UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2, 3, "cc");
            UserProfileEntity entity = new UserProfileEntity();
            
            entity.setUserID(1);
            entity.setPfpUrl("aaa");
            entity.setBio("bbb");
            entity.setLatitude(2);
            entity.setLongitude(3);
            entity.setAddress("ccc");
            
            when(dao.findUserProfileByUserID(1)).thenReturn(entity);
            
            UserProfileEntity entity2 = entity;
            entity.setUserID(1);
            entity.setPfpUrl("aa");
            entity.setBio("bb");
            entity.setLatitude(2);
            entity.setLongitude(3);
            entity.setAddress("cc");
            
            when(dao.save(entity2)).thenReturn(entity2);
            
            UserProfileModel model2 = (service.updateUserProfile(model).get());
            
            assertEquals(model2.getUserID(), model.getUserID());
            assertEquals(model2.getPfpUrl(), model.getPfpUrl());
            assertEquals(model2.getBio(), model.getBio());
            assertEquals(model2.getLatitude(), model.getLatitude());
            assertEquals(model2.getLongitude(), model.getLongitude());
            assertEquals(model2.getAddress(), model.getAddress());
        } catch (NoSuchFieldException ex) {
            fail("There was an Exception.");
        }
    }
    @Test
    public void FailedProfileUpdateTest(){
        when(dao.findUserProfileByUserID(1)).thenReturn(null);

        UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2, 3, "cc");

        assertThrows(NoSuchFieldException.class, () -> {service.updateUserProfile(model);}, "User Profile does not exist.");
    }

    //modelToEntity
    @Test
    public void EntityConversionTest(){
        UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2, 3, "cc");

        UserProfileEntity entity = service.modelToEntity(model);

        assertEquals(entity.getUserID(), model.getUserID());
        assertEquals(entity.getPfpUrl(), model.getPfpUrl());
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
		entity.setPfpUrl("aaa");
		entity.setBio("bbb");
		entity.setLatitude(2);
		entity.setLongitude(3);
		entity.setAddress("ccc");

        UserProfileModel model = service.entityToModel(entity);

        assertEquals(entity.getUserID(), model.getUserID());
        assertEquals(entity.getPfpUrl(), model.getPfpUrl());
        assertEquals(entity.getBio(), model.getBio());
        assertEquals(entity.getLatitude(), model.getLatitude());
        assertEquals(entity.getLongitude(), model.getLongitude());
        assertEquals(entity.getAddress(), model.getAddress());
    }

    //deleteUserProfileByID
    @Test
    public void DeleteById(){
        when(dao.findUserProfileById(1)).thenReturn(null);
        assertTrue(service.deleteUserProfileByID(1));
    }
    @Test
    public void DeleteByIdFailure(){
        UserProfileEntity result = new UserProfileEntity();
        when(dao.findUserProfileById(1)).thenReturn(result);
        assertFalse(service.deleteUserProfileByID(1));
    }

    //deleteUserProfileByUserID
    @Test
    public void DeleteByUserID(){
        when(dao.findUserProfileById(1)).thenReturn(null);
        assertTrue(service.deleteUserProfileByUserID(1));
    }
    @Test
    public void DeleteByUserIDFailure(){
        UserProfileEntity result = new UserProfileEntity();
        when(dao.findUserProfileByUserID(1)).thenReturn(result);
        assertFalse(service.deleteUserProfileByUserID(1));
    }

    //createNewUserProfile
    @Test
    public void CreateProfileTest(){
        try {
            //I have no idea how to accurately test this.
            UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2, 3, "cc");
            UserProfileEntity entity = new UserProfileEntity();
            
            entity.setUserID(1);
            entity.setPfpUrl("aa");
            entity.setBio("bb");
            entity.setLatitude(2);
            entity.setLongitude(3);
            entity.setAddress("cc");
            
            when(dao.findUserProfileByUserID(1)).thenReturn(null);
            
            when(dao.save(entity)).thenReturn(entity);
            
            UserProfileModel model2 = (service.createNewUserProfile(model).get());
            
            assertEquals(model2.getUserID(), model.getUserID());
            assertEquals(model2.getPfpUrl(), model.getPfpUrl());
            assertEquals(model2.getBio(), model.getBio());
            assertEquals(model2.getLatitude(), model.getLatitude());
            assertEquals(model2.getLongitude(), model.getLongitude());
            assertEquals(model2.getAddress(), model.getAddress());
        } catch (NoSuchFieldException ex) {
            fail("There was an Exception.");
        }
    }
    @Test
    public void FailedProfileCreationTest(){
        UserProfileEntity entity = new UserProfileEntity();
        when(dao.findUserProfileByUserID(1)).thenReturn(entity);

        UserProfileModel model = new UserProfileModel(1, "aa", "bb", 2, 3, "cc");

        assertThrows(NoSuchFieldException.class, () -> {service.createNewUserProfile(model);}, "User Profile already exists.");
    }

    //uniqueUserID
    @Test
    public void UniqueIDTest(){
        when(dao.findUserProfileByUserID(1)).thenReturn(null);
        assertTrue(service.uniqueUserID(1));
    }
    @Test
    public void ExistingIDTest(){
        UserProfileEntity result = new UserProfileEntity();
        when(dao.findUserProfileByUserID(1)).thenReturn(result);
        assertFalse(service.uniqueUserID(1));
    }
}