package com.acme.backendunityvolunteer.application.news_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NoticiaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaPublicacion;
    private Long organizacionId;
}
