package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.BacklogDTO;
import com.example.managementsystem.models.entities.Backlog;
import com.example.managementsystem.models.entities.Projet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BacklogMapperImpl implements BacklogMapper {

    @Override
    public BacklogDTO toDTO(Backlog backlog) {
        if ( backlog == null ) {
            return null;
        }

        Long projetId = null;
        Long id = null;
        String titre = null;
        String description = null;
        String etat = null;

        projetId = backlogProjetId( backlog );
        if ( backlog.getId() != null ) {
            id = backlog.getId().longValue();
        }
        titre = backlog.getTitre();
        description = backlog.getDescription();
        etat = backlog.getEtat();

        BacklogDTO backlogDTO = new BacklogDTO( id, titre, description, etat, projetId );

        return backlogDTO;
    }

    @Override
    public Backlog toEntity(BacklogDTO backlogDTO) {
        if ( backlogDTO == null ) {
            return null;
        }

        Backlog backlog = new Backlog();

        backlog.setProjet( backlogDTOToProjet( backlogDTO ) );
        if ( backlogDTO.id() != null ) {
            backlog.setId( backlogDTO.id().intValue() );
        }
        backlog.setTitre( backlogDTO.titre() );
        backlog.setDescription( backlogDTO.description() );
        backlog.setEtat( backlogDTO.etat() );

        return backlog;
    }

    @Override
    public void updateBacklogFromDTO(BacklogDTO backlogDTO, Backlog backlog) {
        if ( backlogDTO == null ) {
            return;
        }

        backlog.setTitre( backlogDTO.titre() );
        backlog.setDescription( backlogDTO.description() );
        backlog.setEtat( backlogDTO.etat() );
    }

    @Override
    public List<BacklogDTO> toDTOs(List<Backlog> backlogs) {
        if ( backlogs == null ) {
            return null;
        }

        List<BacklogDTO> list = new ArrayList<BacklogDTO>( backlogs.size() );
        for ( Backlog backlog : backlogs ) {
            list.add( toDTO( backlog ) );
        }

        return list;
    }

    private Long backlogProjetId(Backlog backlog) {
        if ( backlog == null ) {
            return null;
        }
        Projet projet = backlog.getProjet();
        if ( projet == null ) {
            return null;
        }
        Long id = projet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Projet backlogDTOToProjet(BacklogDTO backlogDTO) {
        if ( backlogDTO == null ) {
            return null;
        }

        Projet projet = new Projet();

        projet.setId( backlogDTO.projetId() );

        return projet;
    }
}
