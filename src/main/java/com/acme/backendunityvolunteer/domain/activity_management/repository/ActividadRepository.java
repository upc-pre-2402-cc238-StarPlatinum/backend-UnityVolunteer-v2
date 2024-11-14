package com.acme.backendunityvolunteer.domain.activity_management.repository;

import com.acme.backendunityvolunteer.domain.activity_management.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    // Obtener todas las actividades de una organización específica
    List<Actividad> findByOrganizacionId(Long organizacionId);

    // Obtener todas las actividades en las que un voluntario está inscrito
    List<Actividad> findByVoluntariosId(Long voluntarioId);
}
