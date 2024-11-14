package com.acme.backendunityvolunteer.domain.user_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sesiones_usuarios")
public class SesionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime inicioSesion;

    @Column(length = 45)
    private String direccionIp;

    @Column(nullable = false)
    private Boolean exito;

    @Column(length = 255)
    private String razonFallo;

    @Column(length = 255)
    private String tokenSesion;
}