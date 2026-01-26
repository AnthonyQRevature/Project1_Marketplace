package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Repository.Entities.PostTagsEntity;

public interface PostTagsDao extends JpaRepository<PostTagsEntity, Integer> {

}
