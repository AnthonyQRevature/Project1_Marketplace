package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.Repository.Entities.TagEntity;

public interface TagDao extends JpaRepository<TagEntity, Integer>{

}
