package com.acme.backendunityvolunteer.application.notification_manegement.dto.notificacion_service;


import com.acme.backendunityvolunteer.application.notification_manegement.dto.NotificationDTO;
import com.acme.backendunityvolunteer.domain.notification_management.repository.NotificationRepository;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.UsuarioRepository;
import com.acme.backendunityvolunteer.domain.notification_management.model.Notificacion;
import com.acme.backendunityvolunteer.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public NotificationDTO obtenerNotificacionPorId(Long id) {
        Notificacion notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificación no encontrada"));

        return mapToDTO(notification);
    }

    public List<NotificationDTO> obtenerNotificacionesPorUsuarioId(Long usuarioId) {
        List<Notificacion> notifications = notificationRepository.findByUsuarioId(usuarioId);
        if (notifications.isEmpty()) {
            throw new NotFoundException("No se encontraron notificaciones para el usuario con ID: " + usuarioId);
        }
        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void eliminarNotificacionPorId(Long id){
        Notificacion notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificación no encontrada"));
        notificationRepository.deleteById(id);
    }

    //Metodo para crear una notificacion
    public NotificationDTO registrarNotificacion(NotificationDTO notificationDTO){
        Notificacion newNotification = new Notificacion();
        newNotification.setTitulo(notificationDTO.getTitulo());
        newNotification.setDescripcion(notificationDTO.getDescripcion());
        newNotification.setFechaGeneracion(notificationDTO.getFechaGeneracion());
        newNotification.setFechaVisualizacion(notificationDTO.getFechaVisualizacion());
        newNotification.setUsuario(usuarioRepository.findById(notificationDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado")));

        Notificacion notificationSaved = notificationRepository.save(newNotification);
        return mapToDTO(notificationSaved);
    }

    private NotificationDTO mapToDTO(Notificacion notificacion) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notificacion.getId());
        dto.setTitulo(notificacion.getTitulo());
        dto.setDescripcion(notificacion.getDescripcion());
        dto.setFechaGeneracion(notificacion.getFechaGeneracion());
        dto.setFechaVisualizacion(notificacion.getFechaVisualizacion());
        dto.setUsuarioId(notificacion.getUsuario().getId());

        return dto;
    }
}
