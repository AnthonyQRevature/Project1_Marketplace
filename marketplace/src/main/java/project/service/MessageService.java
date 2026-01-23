//CHANGED
package project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import project.Repository.Entities.MessageEntity;
import project.Repository.dao.MessageDao;

@Service
public class MessageService {

    private final MessageDao messageDao;

    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    // SEND MESSAGE
    public MessageEntity sendMessage(
            Integer senderId,
            Integer recieverId,
            Integer postId,
            String message
    ) {
        MessageEntity msg = new MessageEntity();
        msg.setSender_id(senderId);
        msg.setReciever_id(recieverId);
        msg.setPost_id(postId);
        msg.setMessage(message);

        return messageDao.save(msg);
    }

    // GET PRIVATE CONVERSATION BETWEEN TWO USERS
    public List<MessageEntity> getConversation(
            Integer user1Id,
            Integer user2Id
    ) {
        return messageDao
                .findBySender_idAndReciever_idOrSender_idAndReciever_id(
                        user1Id, user2Id,
                        user2Id, user1Id
                );
    }
}


/*package project.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import project.Repository.Entities.MessageEntity;
import project.Repository.dao.MessageDao;

@Service
public class MessageService {

    private final MessageDao messageDao;

    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    // SEND MESSAGE
    public MessageEntity sendMessage(
            Integer senderId,
            Integer receiverId,
            Integer postId,
            String messageText
    ) {
        MessageEntity message = new MessageEntity();
        message.setSender_id(senderId);
        message.setReciever_id(receiverId);
        message.setPost_id(postId);
        message.setMessage(messageText);
        message.setSent_at(Instant.now());

        return messageDao.save(message);
    }

    // GET MESSAGES FOR A POST
    public List<MessageEntity> getMessagesForPost(Integer postId) {
        return messageDao.findByPost_id(postId);
    }
}
*/