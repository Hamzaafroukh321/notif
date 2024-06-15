package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.ChatMessage;
import com.example.managementsystem.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


        @Query("SELECT cm FROM ChatMessage cm WHERE cm.sender.matricule = :userId OR cm.recipient.matricule = :userId")
        List<ChatMessage> findBySenderOrRecipient(@Param("userId") Long userId);

        @Query("SELECT cm FROM ChatMessage cm WHERE (cm.sender.matricule = :senderId AND cm.recipient.matricule = :recipientId) OR (cm.sender.matricule = :recipientId AND cm.recipient.matricule = :senderId) ORDER BY cm.timestamp ASC")
        List<ChatMessage> findChatHistory(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId);


}
