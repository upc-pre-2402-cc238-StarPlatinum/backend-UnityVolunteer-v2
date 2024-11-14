package com.acme.backendunityvolunteer.infraestructure.user_management.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String correo;
    private String contrasena;
}