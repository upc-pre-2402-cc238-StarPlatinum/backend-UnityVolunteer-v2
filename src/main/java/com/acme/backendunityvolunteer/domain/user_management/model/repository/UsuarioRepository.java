package com.acme.backendunityvolunteer.domain.user_management.model.repository;

import com.acme.backendunityvolunteer.domain.user_management.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByTelefono(String telefono);
    Optional<Usuario> findByCorreoAndActivoTrue(String correo);
}


