package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.Repository.Entities.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>
{
    UserEntity findUserById(Integer id);

    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    UserEntity findUserByUsername(String username);
}
