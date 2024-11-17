package com.acme.backendunityvolunteer.application.news_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class NoticiaDTO {
    private Long id;

    @NotNull(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha de publicación es obligatoria")
    private Date fechaPublicacion;

    @NotNull(message = "El id de la actividad es obligatorio")
    private Long actividadId;
}
