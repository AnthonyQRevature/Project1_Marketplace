package project.Repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.Repository.Entities.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>
{
    UserEntity findUserByUserId(Integer id);

    //@Query("SELECT u FROM user u WHERE username = :username")
    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    UserEntity findUserByUsername(String username);
}
