package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.models.entities.Audit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:43+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AuditMapperImpl implements AuditMapper {

    @Override
    public AuditDTO auditToAuditDTO(Audit audit) {
        if ( audit == null ) {
            return null;
        }

        AuditDTO auditDTO = new AuditDTO();

        auditDTO.setId( audit.getId() );
        auditDTO.setTimestamp( audit.getTimestamp() );
        auditDTO.setAction( audit.getAction() );
        auditDTO.setDescription( audit.getDescription() );

        return auditDTO;
    }

    @Override
    public Audit auditDTOToAudit(AuditDTO auditDTO) {
        if ( auditDTO == null ) {
            return null;
        }

        Audit audit = new Audit();

        audit.setId( auditDTO.getId() );
        audit.setTimestamp( auditDTO.getTimestamp() );
        audit.setAction( auditDTO.getAction() );
        audit.setDescription( auditDTO.getDescription() );

        return audit;
    }
}
