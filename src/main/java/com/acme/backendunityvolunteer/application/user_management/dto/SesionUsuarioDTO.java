package com.acme.backendunityvolunteer.application.user_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class SesionUsuarioDTO {
    private Long id;
    private Long usuarioId;
    private LocalDateTime inicioSesion;
    private String direccionIp;
    private boolean exito;
    private String razonFallo;
    private String tokenSesion;
}
