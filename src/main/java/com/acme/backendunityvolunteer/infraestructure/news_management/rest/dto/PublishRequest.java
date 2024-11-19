package com.acme.backendunityvolunteer.infraestructure.news_management.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishRequest {

    @NotNull(message = "El titulo no puede estar vacío")
    private String titulo;

    @NotNull(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La fecha de publicación no puede estar vacía")
    private String fechaPublicacion;
}
