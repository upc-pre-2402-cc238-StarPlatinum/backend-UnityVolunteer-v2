package com.acme.backendunityvolunteer.domain.notification_management.repository;


import com.acme.backendunityvolunteer.domain.notification_management.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findById(Long id);
    List<Notificacion> findByUsuarioId(Long usuarioId);
}
