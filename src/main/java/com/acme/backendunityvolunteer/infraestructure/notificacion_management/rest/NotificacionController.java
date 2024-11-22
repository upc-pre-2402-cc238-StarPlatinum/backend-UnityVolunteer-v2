package com.acme.backendunityvolunteer.infraestructure.notificacion_management.rest;


import com.acme.backendunityvolunteer.application.notification_manegement.dto.NotificationDTO;
import com.acme.backendunityvolunteer.application.notification_manegement.dto.notificacion_service.NotificationService;
import com.acme.backendunityvolunteer.application.user_management.dto.user_services.UsuarioService;
import com.acme.backendunityvolunteer.infraestructure.notificacion_management.rest.dto.NotificationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UsuarioService usuarioService;

    // -------------------
    // Gestión de Notificaciones
    // -------------------

    // Registro de una nueva notificación
    @PostMapping("/Registro")
    public ResponseEntity<String> RegistrarNotificacion(@Valid @RequestBody NotificationRequest request) {
        // Crear y guardar la notificacion primero
        NotificationDTO dto = new NotificationDTO();
        dto.setTitulo(request.getTitulo());
        dto.setDescripcion(request.getDescripcion());
        dto.setFechaGeneracion(request.getFechaGeneracion());
        dto.setFechaVisualizacion(request.getFechaVisualizacion());
        dto.setUsuarioId(request.getUserid());

        NotificationDTO notificacionGuardada = notificationService.registrarNotificacion(dto);
        return ResponseEntity.ok("Notificación generada con éxito");
    }

    //Obtener notificacion por su ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> obtenerNotificacionId(@PathVariable Long id) {
        NotificationDTO notificacion = notificationService.obtenerNotificacionPorId(id);
        return ResponseEntity.ok(notificacion);
    }

    //Obtener notificaciones de un usuario por su ID
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<NotificationDTO>> obtenerNotificacionesPorUsuarioId(@PathVariable Long id) {
        List<NotificationDTO> notificacions = notificationService.obtenerNotificacionesPorUsuarioId(id);
        return ResponseEntity.ok(notificacions);
    }

    //Eliminar notificaciones por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacionPorId(@PathVariable Long id) {
        notificationService.eliminarNotificacionPorId(id);
        return ResponseEntity.noContent().build();
    }
}
