package com.example.managementsystem.DTO;

import java.time.LocalDateTime;

//Donne moi les annotations de la classe lombok

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AuditDTO {
    private Long id;
    private LocalDateTime timestamp;
    private String action;
    private String description;


}
