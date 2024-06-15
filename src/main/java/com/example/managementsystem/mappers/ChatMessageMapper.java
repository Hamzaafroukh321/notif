package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.ChatMessageDTO;
import com.example.managementsystem.models.entities.ChatMessage;
import com.example.managementsystem.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMessageMapper {

    @Mapping(target = "senderId", source = "sender.matricule")
    @Mapping(target = "recipientId", source = "recipient.matricule")
    ChatMessageDTO toDTO(ChatMessage chatMessage);

    @Mapping(target = "sender", source = "senderId", qualifiedByName = "fromId")
    @Mapping(target = "recipient", source = "recipientId", qualifiedByName = "fromId")
    @Mapping(target = "id", ignore = true)  // Assuming ID is auto-generated
    ChatMessage toEntity(ChatMessageDTO chatMessageDTO);

    @Named("fromId")
    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setMatricule(id);
        return user;
    }
}