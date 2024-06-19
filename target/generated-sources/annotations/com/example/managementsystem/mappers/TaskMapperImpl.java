package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.models.entities.Sprint;
import com.example.managementsystem.models.entities.Task;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.enums.TaskStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:43+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDTO toDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        Long sprintId = null;
        Long assignedToMatricule = null;
        Long id = null;
        String description = null;
        TaskStatus status = null;

        sprintId = taskSprintId( task );
        assignedToMatricule = taskAssignedToMatricule( task );
        id = task.getId();
        description = task.getDescription();
        status = task.getStatus();

        TaskDTO taskDTO = new TaskDTO( id, description, status, sprintId, assignedToMatricule );

        return taskDTO;
    }

    @Override
    public Task toEntity(TaskDTO taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        Task task = new Task();

        task.setSprint( taskDTOToSprint( taskDTO ) );
        task.setAssignedTo( taskDTOToUser( taskDTO ) );
        task.setId( taskDTO.id() );
        task.setDescription( taskDTO.description() );
        task.setStatus( taskDTO.status() );

        return task;
    }

    @Override
    public void updateTaskFromDTO(TaskDTO taskDTO, Task task) {
        if ( taskDTO == null ) {
            return;
        }

        if ( task.getSprint() == null ) {
            task.setSprint( new Sprint() );
        }
        taskDTOToSprint1( taskDTO, task.getSprint() );
        if ( task.getAssignedTo() == null ) {
            task.setAssignedTo( new User() );
        }
        taskDTOToUser1( taskDTO, task.getAssignedTo() );
        task.setDescription( taskDTO.description() );
        task.setStatus( taskDTO.status() );
    }

    private Long taskSprintId(Task task) {
        if ( task == null ) {
            return null;
        }
        Sprint sprint = task.getSprint();
        if ( sprint == null ) {
            return null;
        }
        Long id = sprint.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long taskAssignedToMatricule(Task task) {
        if ( task == null ) {
            return null;
        }
        User assignedTo = task.getAssignedTo();
        if ( assignedTo == null ) {
            return null;
        }
        Long matricule = assignedTo.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }

    protected Sprint taskDTOToSprint(TaskDTO taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        Sprint sprint = new Sprint();

        sprint.setId( taskDTO.sprintId() );

        return sprint;
    }

    protected User taskDTOToUser(TaskDTO taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        User user = new User();

        user.setMatricule( taskDTO.assignedToMatricule() );

        return user;
    }

    protected void taskDTOToSprint1(TaskDTO taskDTO, Sprint mappingTarget) {
        if ( taskDTO == null ) {
            return;
        }

        mappingTarget.setId( taskDTO.sprintId() );
    }

    protected void taskDTOToUser1(TaskDTO taskDTO, User mappingTarget) {
        if ( taskDTO == null ) {
            return;
        }

        mappingTarget.setMatricule( taskDTO.assignedToMatricule() );
    }
}
