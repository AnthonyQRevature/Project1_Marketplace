package project.Repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.Repository.Entities.MessageEntity;

@Repository
public interface MessageDao extends JpaRepository<MessageEntity, Integer> {

    List<MessageEntity> findBySender_idAndReciever_id(Integer senderId, Integer receiverId);

    List<MessageEntity> findByPost_id(Integer postId);
}
