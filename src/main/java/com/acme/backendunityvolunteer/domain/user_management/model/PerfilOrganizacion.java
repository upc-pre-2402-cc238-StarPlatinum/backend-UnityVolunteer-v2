package com.acme.backendunityvolunteer.domain.user_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil_organizacion")
public class PerfilOrganizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 255)
    private String nombreOrganizacion;

    @Column(nullable = false, length = 100)
    private String tipoOrganizacion;

    @Column(length = 255)
    private String sitioWeb;
}
