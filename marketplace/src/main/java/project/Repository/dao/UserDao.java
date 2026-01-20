package project.Repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import project.Repository.Entities.UserDistanceEntity;
import project.Repository.Entities.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>
{
    UserEntity findUserById(Integer id);

    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    UserEntity findUserByUsername(String username);

    @NativeQuery("SELECT id, username, pfp_encoded, haversine_formula(?1, ?2, latitude, longitude) as distance FROM user_distances")
    List<UserDistanceEntity> findUserByDistance(double latitude, double longitude);
}
