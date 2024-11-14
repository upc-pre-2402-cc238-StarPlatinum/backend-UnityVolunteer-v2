package com.acme.backendunityvolunteer.domain.user_management.model.repository;

import com.acme.backendunityvolunteer.domain.user_management.model.PerfilVoluntario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilVoluntarioRepository extends JpaRepository<PerfilVoluntario, Long> {
    PerfilVoluntario findByUsuarioId(Long usuarioId);
}