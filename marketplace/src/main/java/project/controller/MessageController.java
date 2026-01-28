//CHANGED
package project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.MessageEntity;
import project.controller.response.ProfileBriefResponse;
import project.service.MessageService;
import project.util.AllowCORS;
import project.util.Secure;

@RestController
@RequestMapping("/messages")
@AllowCORS
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // SEND MESSAGE
    @PostMapping("/send")
    public ResponseEntity<MessageEntity> sendMessage(
            @RequestParam Integer senderId,
            @RequestParam Integer receiverId,
//          @RequestParam Integer postId,
            @RequestBody String message
    ) {
        return ResponseEntity.ok(messageService.sendMessage(
            senderId,
            receiverId,
            message
        ));
    }

    // GET PRIVATE CONVERSATION BETWEEN TWO PEOPLE
    @GetMapping("/conversation")
    public ResponseEntity<List<MessageEntity>> getConversation(
            @RequestParam Integer user1Id,
            @RequestParam Integer user2Id
    ) {
        return ResponseEntity.ok(messageService.getConversation(user1Id, user2Id));
    }

    // get list of conversations for a given user
    @GetMapping("/{id}")
    @Secure
    public ResponseEntity<List<ProfileBriefResponse>> getConversations(
        @RequestHeader("Authorization") String auth,
        @PathVariable int id
    ) {
        return ResponseEntity.ok(messageService.getConversations(id));
    }
}



/*package project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import project.Repository.Entities.MessageEntity;
import project.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // SEND MESSAGE
    @PostMapping("/send")
    public MessageEntity sendMessage( //dont put message in message parameter message
            @RequestParam Integer senderId,
            @RequestParam Integer recieverId,
            @RequestParam Integer postId,
            @RequestParam String message
    ) {
        return messageService.sendMessage(senderId, recieverId, postId, message);
    }

    // GET MESSAGES FOR A POST
    @GetMapping("/post/{postId}")
    public List<MessageEntity> getMessagesForPost(@PathVariable Integer postId) { //specify between which 2 ppl
        return messageService.getMessagesForPost(postId); //and which 2 front end
    }
}

*/