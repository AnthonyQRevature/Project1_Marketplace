//CHANGED
package project.service;

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
//          Integer postId,
            String message
    ) {
        MessageEntity msg = new MessageEntity();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setMessage(message);
        msg.setSentAt(Instant.now());

        return messageDao.save(msg);
    }

    // GET PRIVATE CONVERSATION BETWEEN TWO USERS
    public List<MessageEntity> getConversation(
            Integer user1Id,
            Integer user2Id
    ) {
        return messageDao
                .findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
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