package com.acme.backendunityvolunteer.domain.user_management.model.repository;

import com.acme.backendunityvolunteer.domain.user_management.model.SesionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, Long> {
    List<SesionUsuario> findByUsuarioId(Long usuarioId);
}