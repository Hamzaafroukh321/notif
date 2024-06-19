package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.ChatMessageDTO;
import com.example.managementsystem.models.entities.ChatMessage;
import com.example.managementsystem.models.entities.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ChatMessageMapperImpl implements ChatMessageMapper {

    @Override
    public ChatMessageDTO toDTO(ChatMessage chatMessage) {
        if ( chatMessage == null ) {
            return null;
        }

        Long senderId = null;
        Long recipientId = null;
        Long id = null;
        String content = null;
        LocalDateTime timestamp = null;

        senderId = chatMessageSenderMatricule( chatMessage );
        recipientId = chatMessageRecipientMatricule( chatMessage );
        id = chatMessage.getId();
        content = chatMessage.getContent();
        timestamp = chatMessage.getTimestamp();

        boolean isPrivate = false;

        ChatMessageDTO chatMessageDTO = new ChatMessageDTO( id, senderId, recipientId, content, timestamp, isPrivate );

        return chatMessageDTO;
    }

    @Override
    public ChatMessage toEntity(ChatMessageDTO chatMessageDTO) {
        if ( chatMessageDTO == null ) {
            return null;
        }

        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setSender( fromId( chatMessageDTO.senderId() ) );
        chatMessage.setRecipient( fromId( chatMessageDTO.recipientId() ) );
        chatMessage.setContent( chatMessageDTO.content() );
        chatMessage.setTimestamp( chatMessageDTO.timestamp() );

        return chatMessage;
    }

    private Long chatMessageSenderMatricule(ChatMessage chatMessage) {
        if ( chatMessage == null ) {
            return null;
        }
        User sender = chatMessage.getSender();
        if ( sender == null ) {
            return null;
        }
        Long matricule = sender.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }

    private Long chatMessageRecipientMatricule(ChatMessage chatMessage) {
        if ( chatMessage == null ) {
            return null;
        }
        User recipient = chatMessage.getRecipient();
        if ( recipient == null ) {
            return null;
        }
        Long matricule = recipient.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }
}
