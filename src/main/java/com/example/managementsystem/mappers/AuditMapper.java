package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.models.entities.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring"  , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditMapper {
    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

    AuditDTO auditToAuditDTO(Audit audit);
    Audit auditDTOToAudit(AuditDTO auditDTO);
}
