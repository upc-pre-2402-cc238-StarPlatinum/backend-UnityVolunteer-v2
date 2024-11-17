package com.acme.backendunityvolunteer.application.activity_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActividadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String hora;
    private String duracion;
    private String lugar;
    private String tipo;
    private int personasMinimo;
    private int personasMaximo;
    private int totalPersonasInscritas;
    private Long organizacionId;
    private int puntuacionActividad;
}