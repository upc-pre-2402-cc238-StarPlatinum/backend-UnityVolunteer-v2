package com.acme.backendunityvolunteer.domain.user_management.model.repository;

import com.acme.backendunityvolunteer.domain.user_management.model.PerfilOrganizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilOrganizacionRepository extends JpaRepository<PerfilOrganizacion, Long> {
    PerfilOrganizacion findByUsuarioId(Long usuarioId);
}