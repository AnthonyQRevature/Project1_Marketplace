package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.Repository.Entities.UserProfileEntity;

public interface UserProfileDao extends JpaRepository<UserProfileEntity, Integer> {
    Boolean existsByUserID(Integer userId);
    UserProfileEntity findUserProfileByUserID(Integer user_id);
    Integer deleteByUserID(Integer user_id);
}
