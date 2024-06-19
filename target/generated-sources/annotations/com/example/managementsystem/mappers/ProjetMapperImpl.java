package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.ProjetDTO;
import com.example.managementsystem.models.entities.Projet;
import com.example.managementsystem.models.entities.User;
import java.time.LocalDate;
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
public class ProjetMapperImpl implements ProjetMapper {

    @Override
    public ProjetDTO toDTO(Projet projet) {
        if ( projet == null ) {
            return null;
        }

        Long chefMatricule = null;
        List<Long> teamMembersMatricules = null;
        Long id = null;
        String nom = null;
        String description = null;
        LocalDate dateDebut = null;
        LocalDate dateFin = null;
        String mode = null;
        String status = null;

        chefMatricule = projetChefMatricule( projet );
        teamMembersMatricules = usersToMatricules( projet.getTeamMembers() );
        id = projet.getId();
        nom = projet.getNom();
        description = projet.getDescription();
        dateDebut = projet.getDateDebut();
        dateFin = projet.getDateFin();
        mode = projet.getMode();
        status = projet.getStatus();

        ProjetDTO projetDTO = new ProjetDTO( id, nom, description, dateDebut, dateFin, mode, status, chefMatricule, teamMembersMatricules );

        return projetDTO;
    }

    @Override
    public Projet toEntity(ProjetDTO projetDTO, List<User> users) {
        if ( projetDTO == null ) {
            return null;
        }

        Projet projet = new Projet();

        projet.setChef( findUserByMatricule( projetDTO.chefMatricule(), users ) );
        projet.setTeamMembers( findUsersByMatricules( projetDTO.teamMembersMatricules(), users ) );
        projet.setId( projetDTO.id() );
        projet.setNom( projetDTO.nom() );
        projet.setDescription( projetDTO.description() );
        projet.setDateDebut( projetDTO.dateDebut() );
        projet.setDateFin( projetDTO.dateFin() );
        projet.setMode( projetDTO.mode() );
        projet.setStatus( projetDTO.status() );

        return projet;
    }

    @Override
    public void updateProjetFromDTO(ProjetDTO projetDTO, Projet projet) {
        if ( projetDTO == null ) {
            return;
        }

        projet.setNom( projetDTO.nom() );
        projet.setDescription( projetDTO.description() );
        projet.setDateDebut( projetDTO.dateDebut() );
        projet.setDateFin( projetDTO.dateFin() );
        projet.setMode( projetDTO.mode() );
        projet.setStatus( projetDTO.status() );
    }

    @Override
    public List<ProjetDTO> toDTOs(List<Projet> projets) {
        if ( projets == null ) {
            return null;
        }

        List<ProjetDTO> list = new ArrayList<ProjetDTO>( projets.size() );
        for ( Projet projet : projets ) {
            list.add( toDTO( projet ) );
        }

        return list;
    }

    private Long projetChefMatricule(Projet projet) {
        if ( projet == null ) {
            return null;
        }
        User chef = projet.getChef();
        if ( chef == null ) {
            return null;
        }
        Long matricule = chef.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }
}
