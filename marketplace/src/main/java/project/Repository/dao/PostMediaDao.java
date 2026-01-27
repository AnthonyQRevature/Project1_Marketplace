package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.PostMediaEntity;

import java.util.List;

public interface PostMediaDao extends JpaRepository<PostMediaEntity, Integer>{
    @Query("SELECT p FROM PostMediaEntity p WHERE p.postId = ?1")
    List<PostMediaEntity> findByPostId(Integer postId);

}
