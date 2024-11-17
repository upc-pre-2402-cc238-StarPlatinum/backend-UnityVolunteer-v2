package com.acme.backendunityvolunteer.application.activity_management.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoluntarioInscritoDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String intereses;
    private String experiencia;
    private String disponibilidad;
    private int puntuacion;

    public VoluntarioInscritoDTO(Long id, String nombre, String correo, String telefono, String intereses, String experiencia, String disponibilidad,int puntuacion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.intereses = intereses;
        this.experiencia = experiencia;
        this.disponibilidad = disponibilidad;
        this.puntuacion = puntuacion;
    }
}
