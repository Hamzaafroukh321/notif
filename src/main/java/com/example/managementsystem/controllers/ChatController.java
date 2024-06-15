package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.example.managementsystem.services.ChatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {
        // Create a new ChatMessageDTO with the sender's ID
        ChatMessageDTO updatedChatMessageDTO = new ChatMessageDTO(
                null,  // Assuming ID will be auto-generated
                chatMessageDTO.senderId(),
                chatMessageDTO.recipientId(),
                chatMessageDTO.content(),
                LocalDateTime.now(),
                chatMessageDTO.isPrivate()
        );
        System.out.println("Updated ChatMessageDTO: " + updatedChatMessageDTO);

        // Save the chat message
        ChatMessageDTO savedChatMessageDTO = chatService.saveMessage(updatedChatMessageDTO);
        System.out.println("Saved ChatMessageDTO: " + savedChatMessageDTO);

        // Send the message to the recipient
        messagingTemplate.convertAndSendToUser(
                savedChatMessageDTO.recipientId().toString(),
                "/queue/messages",
                savedChatMessageDTO
        );

        // Log for debugging
        System.out.println("Message sent to user: " + savedChatMessageDTO.recipientId());
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@RequestParam Long senderId, @RequestParam Long recipientId) {
        List<ChatMessageDTO> messages = chatService.getChatHistory(senderId, recipientId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unique-conversations")
    public ResponseEntity<List<Long>> getUniqueConversations(@RequestParam Long userMatricule) {
        List<Long> conversations = chatService.getUniqueConversations(userMatricule);
        return ResponseEntity.ok(conversations);
    }
}