package com.acme.backendunityvolunteer.application.notification_manegement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Date fechaGeneracion;
    private Date fechaVisualizacion;
    private Long usuarioId;
}
