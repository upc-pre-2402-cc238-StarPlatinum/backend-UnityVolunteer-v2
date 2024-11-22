package com.acme.backendunityvolunteer.infraestructure.notificacion_management.rest.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationRequest {

    @NotNull(message = "El titulo no puede estar vacío")
    @Size(min = 10, message = "El titulo debe tener al menos 5 caracteres")
    private String titulo;

    @NotNull(message = "La descripcion no puede estar vacía")
    @Size(min = 10, message = "La descripción debe tener al menos 5 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de generación no puede estar vacía")
    private Date fechaGeneracion;

    @NotNull(message = "La fecha de visualización no puede estar vacía")
    private Date fechaVisualizacion;

    @NotNull(message = "El id del usuario no puede estar vacío")
    private Long userid;
}