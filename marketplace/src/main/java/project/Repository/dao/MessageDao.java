//CHANGED
package project.Repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.Repository.Entities.MessageEntity;

@Repository
public interface MessageDao extends JpaRepository<MessageEntity, Integer> {

    // Messages sent from A to B
    List<MessageEntity> findBySender_idAndReciever_id(
            Integer senderId,
            Integer recieverId
    );

    // Full private conversation between two users
    List<MessageEntity> findBySender_idAndReciever_idOrSender_idAndReciever_id(
            Integer sender1,
            Integer reciever1,
            Integer sender2,
            Integer reciever2
    );
}


/*
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
*/



