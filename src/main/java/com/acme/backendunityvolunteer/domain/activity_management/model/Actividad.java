package com.acme.backendunityvolunteer.domain.activity_management.model;

import com.acme.backendunityvolunteer.domain.user_management.model.PerfilOrganizacion;
import com.acme.backendunityvolunteer.domain.user_management.model.PerfilVoluntario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String hora;

    @Column(nullable = false)
    private String duracion;

    @Column(nullable = false, length = 200)
    private String lugar;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false)
    private int personasMinimo;

    @Column(nullable = false)
    private int personasMaximo;

    @Column(nullable = false)
    private int totalPersonasInscritas = 0;

    // Relación con la organización que crea la actividad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", nullable = false)
    private PerfilOrganizacion organizacion;

    // Relación con voluntarios inscritos
    @ManyToMany
    @JoinTable(
            name = "actividad_voluntarios",
            joinColumns = @JoinColumn(name = "actividad_id"),
            inverseJoinColumns = @JoinColumn(name = "voluntario_id")
    )
    private List<PerfilVoluntario> voluntarios;
}
