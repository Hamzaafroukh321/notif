package com.example.managementsystem.services;

import com.example.managementsystem.DTO.ChatMessageDTO;
import com.example.managementsystem.models.entities.ChatMessage;
import com.example.managementsystem.models.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.managementsystem.mappers.ChatMessageMapper;
import com.example.managementsystem.repositories.ChatMessageRepository;
import com.example.managementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


import static com.jayway.jsonpath.internal.function.ParamType.JSON;




@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    public ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.toDTO(savedChatMessage);
    }

    public List<ChatMessageDTO> getChatHistory(Long senderId, Long recipientId) {
        List<ChatMessage> messages = chatMessageRepository.findChatHistory(senderId, recipientId);
        return messages.stream().map(chatMessageMapper::toDTO).collect(Collectors.toList());
    }

    public List<Long> getUniqueConversations(Long userId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderOrRecipient(userId);
        Set<Long> uniqueConversations = new HashSet<>();
        for (ChatMessage message : messages) {
            if (message.getSender().getMatricule().equals(userId)) {
                uniqueConversations.add(message.getRecipient().getMatricule());
            } else {
                uniqueConversations.add(message.getSender().getMatricule());
            }
        }
        return new ArrayList<>(uniqueConversations);
    }
}
