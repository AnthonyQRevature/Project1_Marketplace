package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.Repository.Entities.UserProfileEntity;

public interface UserProfileDao extends JpaRepository<UserProfileEntity, Integer> {
    UserProfileEntity findUserProfileByUser_id(Integer user_id);
    Integer deleteByUser_id(Integer user_id);
}
