package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.PostEntity;

import java.util.List;

public interface PostDao extends JpaRepository<PostEntity, Integer> {
    @Query("SELECT p FROM PostEntity p WHERE p.sellerId = ?1")
    List<PostEntity> findPostBySellerId(Integer sellerId);
}
