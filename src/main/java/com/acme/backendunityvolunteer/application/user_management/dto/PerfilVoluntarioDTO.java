package com.acme.backendunityvolunteer.application.user_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilVoluntarioDTO {
    private Long id;
    private Long usuarioId;
    private String intereses;
    private String experiencia;
    private String disponibilidad;
}
