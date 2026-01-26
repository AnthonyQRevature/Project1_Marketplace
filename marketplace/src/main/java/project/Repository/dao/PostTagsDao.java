package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.PostTagsEntity;

import java.util.List;

public interface PostTagsDao extends JpaRepository<PostTagsEntity, Integer> {
    @Query("SELECT t FROM PostTagsEntity t WHERE t.post = ?1")
    List<PostTagsEntity> findByPost(Integer postId);
}
