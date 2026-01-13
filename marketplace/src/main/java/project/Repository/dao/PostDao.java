package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.Repository.Entities.PostEntity;

public interface PostDao extends JpaRepository<PostEntity, Integer> {
    
}
