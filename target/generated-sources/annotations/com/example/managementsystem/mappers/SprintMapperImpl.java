package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.SprintDTO;
import com.example.managementsystem.models.entities.Projet;
import com.example.managementsystem.models.entities.Sprint;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class SprintMapperImpl implements SprintMapper {

    @Override
    public SprintDTO toDTO(Sprint sprint) {
        if ( sprint == null ) {
            return null;
        }

        Long projetId = null;
        Long id = null;
        String nom = null;
        LocalDate dateDebut = null;
        LocalDate dateFin = null;

        projetId = sprintProjetId( sprint );
        id = sprint.getId();
        nom = sprint.getNom();
        dateDebut = sprint.getDateDebut();
        dateFin = sprint.getDateFin();

        SprintDTO sprintDTO = new SprintDTO( id, nom, dateDebut, dateFin, projetId );

        return sprintDTO;
    }

    @Override
    public Sprint toEntity(SprintDTO sprintDTO) {
        if ( sprintDTO == null ) {
            return null;
        }

        Sprint sprint = new Sprint();

        sprint.setProjet( sprintDTOToProjet( sprintDTO ) );
        sprint.setId( sprintDTO.id() );
        sprint.setNom( sprintDTO.nom() );
        sprint.setDateDebut( sprintDTO.dateDebut() );
        sprint.setDateFin( sprintDTO.dateFin() );

        return sprint;
    }

    @Override
    public void updateSprintFromDTO(SprintDTO sprintDTO, Sprint sprint) {
        if ( sprintDTO == null ) {
            return;
        }

        sprint.setNom( sprintDTO.nom() );
        sprint.setDateDebut( sprintDTO.dateDebut() );
        sprint.setDateFin( sprintDTO.dateFin() );
    }

    private Long sprintProjetId(Sprint sprint) {
        if ( sprint == null ) {
            return null;
        }
        Projet projet = sprint.getProjet();
        if ( projet == null ) {
            return null;
        }
        Long id = projet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Projet sprintDTOToProjet(SprintDTO sprintDTO) {
        if ( sprintDTO == null ) {
            return null;
        }

        Projet projet = new Projet();

        projet.setId( sprintDTO.projetId() );

        return projet;
    }
}
