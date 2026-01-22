package project.controller;

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
    public MessageEntity sendMessage(
            @RequestParam Integer senderId,
            @RequestParam Integer recieverId,
            @RequestParam Integer postId,
            @RequestParam String message
    ) {
        return messageService.sendMessage(senderId, recieverId, postId, message);
    }

    // GET MESSAGES FOR A POST
    @GetMapping("/post/{postId}")
    public List<MessageEntity> getMessagesForPost(@PathVariable Integer postId) {
        return messageService.getMessagesForPost(postId);
    }
}
