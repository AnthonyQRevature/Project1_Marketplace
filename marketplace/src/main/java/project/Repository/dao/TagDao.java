package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.TagEntity;

public interface TagDao extends JpaRepository<TagEntity, Integer>{
    @Query("SELECT t FROM TagEntity t WHERE t.id = ?1")
    TagEntity getTagById(Integer id);
}
