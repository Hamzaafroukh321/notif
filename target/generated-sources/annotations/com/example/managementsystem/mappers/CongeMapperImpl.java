package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.CongeDTO;
import com.example.managementsystem.models.entities.Congees;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.enums.CongeStatus;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CongeMapperImpl implements CongeMapper {

    @Override
    public CongeDTO toDTO(Congees conge) {
        if ( conge == null ) {
            return null;
        }

        Long requestedByMatricule = null;
        Long remplacantMatricule = null;
        Long approvedOrRejectedByMatricule = null;
        Long id = null;
        LocalDate dateDebut = null;
        LocalDate dateFin = null;
        String motif = null;
        CongeStatus status = null;

        requestedByMatricule = congeRequestedByMatricule( conge );
        remplacantMatricule = congeRemplacantMatricule( conge );
        approvedOrRejectedByMatricule = congeApprovedOrRejectedByMatricule( conge );
        id = conge.getId();
        dateDebut = conge.getDateDebut();
        dateFin = conge.getDateFin();
        motif = conge.getMotif();
        status = conge.getStatus();

        CongeDTO congeDTO = new CongeDTO( id, dateDebut, dateFin, motif, status, requestedByMatricule, remplacantMatricule, approvedOrRejectedByMatricule );

        return congeDTO;
    }

    @Override
    public Congees toEntity(CongeDTO congeDTO) {
        if ( congeDTO == null ) {
            return null;
        }

        Congees congees = new Congees();

        congees.setId( congeDTO.id() );
        congees.setDateDebut( congeDTO.dateDebut() );
        congees.setDateFin( congeDTO.dateFin() );
        congees.setMotif( congeDTO.motif() );
        congees.setStatus( congeDTO.status() );

        return congees;
    }

    @Override
    public void updateCongeesFromDTO(CongeDTO congeDTO, Congees congees) {
        if ( congeDTO == null ) {
            return;
        }

        congees.setDateDebut( congeDTO.dateDebut() );
        congees.setDateFin( congeDTO.dateFin() );
        congees.setMotif( congeDTO.motif() );
        congees.setStatus( congeDTO.status() );
    }

    private Long congeRequestedByMatricule(Congees congees) {
        if ( congees == null ) {
            return null;
        }
        User requestedBy = congees.getRequestedBy();
        if ( requestedBy == null ) {
            return null;
        }
        Long matricule = requestedBy.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }

    private Long congeRemplacantMatricule(Congees congees) {
        if ( congees == null ) {
            return null;
        }
        User remplacant = congees.getRemplacant();
        if ( remplacant == null ) {
            return null;
        }
        Long matricule = remplacant.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }

    private Long congeApprovedOrRejectedByMatricule(Congees congees) {
        if ( congees == null ) {
            return null;
        }
        User approvedOrRejectedBy = congees.getApprovedOrRejectedBy();
        if ( approvedOrRejectedBy == null ) {
            return null;
        }
        Long matricule = approvedOrRejectedBy.getMatricule();
        if ( matricule == null ) {
            return null;
        }
        return matricule;
    }
}
