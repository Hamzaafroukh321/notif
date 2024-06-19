package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.UserStoryDTO;
import com.example.managementsystem.models.entities.Backlog;
import com.example.managementsystem.models.entities.UserStory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserStoryMapperImpl implements UserStoryMapper {

    @Override
    public UserStoryDTO toDTO(UserStory userStory) {
        if ( userStory == null ) {
            return null;
        }

        Integer backlogId = null;
        Long id = null;
        String description = null;
        String priority = null;

        backlogId = userStoryBacklogId( userStory );
        id = userStory.getId();
        description = userStory.getDescription();
        priority = userStory.getPriority();

        UserStoryDTO userStoryDTO = new UserStoryDTO( id, description, priority, backlogId );

        return userStoryDTO;
    }

    @Override
    public UserStory toEntity(UserStoryDTO userStoryDTO) {
        if ( userStoryDTO == null ) {
            return null;
        }

        UserStory userStory = new UserStory();

        userStory.setBacklog( userStoryDTOToBacklog( userStoryDTO ) );
        userStory.setId( userStoryDTO.id() );
        userStory.setDescription( userStoryDTO.description() );
        userStory.setPriority( userStoryDTO.priority() );

        return userStory;
    }

    @Override
    public void updateUserStoryFromDTO(UserStoryDTO userStoryDTO, UserStory userStory) {
        if ( userStoryDTO == null ) {
            return;
        }

        userStory.setDescription( userStoryDTO.description() );
        userStory.setPriority( userStoryDTO.priority() );
    }

    private Integer userStoryBacklogId(UserStory userStory) {
        if ( userStory == null ) {
            return null;
        }
        Backlog backlog = userStory.getBacklog();
        if ( backlog == null ) {
            return null;
        }
        Integer id = backlog.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Backlog userStoryDTOToBacklog(UserStoryDTO userStoryDTO) {
        if ( userStoryDTO == null ) {
            return null;
        }

        Backlog backlog = new Backlog();

        backlog.setId( userStoryDTO.backlogId() );

        return backlog;
    }
}
