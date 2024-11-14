package com.acme.backendunityvolunteer.application.activity_management.dto.user_services;

import com.acme.backendunityvolunteer.application.activity_management.dto.ActividadDTO;
import com.acme.backendunityvolunteer.application.activity_management.dto.VoluntarioInscritoDTO;
import com.acme.backendunityvolunteer.domain.activity_management.model.Actividad;
import com.acme.backendunityvolunteer.domain.activity_management.repository.ActividadRepository;
import com.acme.backendunityvolunteer.domain.user_management.model.PerfilOrganizacion;
import com.acme.backendunityvolunteer.domain.user_management.model.PerfilVoluntario;
import com.acme.backendunityvolunteer.domain.user_management.model.Usuario;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.PerfilOrganizacionRepository;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.PerfilVoluntarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private PerfilOrganizacionRepository perfilOrganizacionRepository;

    @Autowired
    private PerfilVoluntarioRepository perfilVoluntarioRepository;

    // Método para crear una nueva actividad
    @Transactional
    public ActividadDTO crearActividad(ActividadDTO actividadDTO) {
        PerfilOrganizacion organizacion = perfilOrganizacionRepository.findById(actividadDTO.getOrganizacionId())
                .orElseThrow(() -> new RuntimeException("Organización no encontrada con ID: " + actividadDTO.getOrganizacionId()));

        Actividad actividad = new Actividad();
        actividad.setNombre(actividadDTO.getNombre());
        actividad.setDescripcion(actividadDTO.getDescripcion());
        actividad.setFecha(actividadDTO.getFecha());
        actividad.setHora(actividadDTO.getHora());
        actividad.setDuracion(actividadDTO.getDuracion());
        actividad.setLugar(actividadDTO.getLugar());
        actividad.setTipo(actividadDTO.getTipo());
        actividad.setPersonasMinimo(actividadDTO.getPersonasMinimo());
        actividad.setPersonasMaximo(actividadDTO.getPersonasMaximo());
        actividad.setTotalPersonasInscritas(0);
        actividad.setOrganizacion(organizacion);

        actividadRepository.save(actividad);
        return mapToDTO(actividad);
    }

    // Método para inscribir un voluntario en una actividad
    @Transactional
    public void inscribirVoluntario(Long actividadId, Long voluntarioId) {
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + actividadId));
        PerfilVoluntario voluntario = perfilVoluntarioRepository.findById(voluntarioId)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado con ID: " + voluntarioId));

        if (actividad.getTotalPersonasInscritas() >= actividad.getPersonasMaximo()) {
            throw new RuntimeException("La actividad ha alcanzado el número máximo de participantes");
        }

        actividad.getVoluntarios().add(voluntario);
        actividad.setTotalPersonasInscritas(actividad.getTotalPersonasInscritas() + 1);
        actividadRepository.save(actividad);
    }

    // Método para listar todas las actividades
    public List<ActividadDTO> listarActividades() {
        return actividadRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Obtener detalles de una actividad específica
    public ActividadDTO obtenerDetallesActividad(Long actividadId) {
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + actividadId));
        return mapToDTO(actividad);
    }

    // Obtener lista de voluntarios inscritos en una actividad
    public List<String> obtenerVoluntariosInscritos(Long actividadId) {
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + actividadId));

        return actividad.getVoluntarios().stream()
                .map(voluntario -> voluntario.getUsuario().getNombre())  // Cambiar según los datos que quieras mostrar de cada voluntario
                .collect(Collectors.toList());
    }

    // Obtener actividades por organización
    public List<ActividadDTO> obtenerActividadesPorOrganizacion(Long organizacionId) {
        List<Actividad> actividades = actividadRepository.findByOrganizacionId(organizacionId);
        return actividades.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    // Obtener actividades por voluntario
    public List<ActividadDTO> obtenerActividadesPorVoluntario(Long voluntarioId) {
        List<Actividad> actividades = actividadRepository.findByVoluntariosId(voluntarioId);
        return actividades.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VoluntarioInscritoDTO> listarVoluntariosDeActividad(Long actividadId) {
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + actividadId));

        return actividad.getVoluntarios().stream().map(voluntario -> {
            Usuario usuario = voluntario.getUsuario(); // Asumiendo que `PerfilVoluntario` tiene una referencia a `Usuario`
            return new VoluntarioInscritoDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getTelefono(),
                    voluntario.getIntereses(),
                    voluntario.getExperiencia(),
                    voluntario.getDisponibilidad()
            );
        }).collect(Collectors.toList());
    }


    // Eliminar una actividad
    @Transactional
    public void eliminarActividad(Long actividadId) {
        if (!actividadRepository.existsById(actividadId)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + actividadId);
        }
        actividadRepository.deleteById(actividadId);
    }

    // Método auxiliar para convertir Actividad a ActividadDTO
    private ActividadDTO mapToDTO(Actividad actividad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setId(actividad.getId());
        dto.setNombre(actividad.getNombre());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setFecha(actividad.getFecha());
        dto.setHora(actividad.getHora());
        dto.setDuracion(actividad.getDuracion());
        dto.setLugar(actividad.getLugar());
        dto.setTipo(actividad.getTipo());
        dto.setPersonasMinimo(actividad.getPersonasMinimo());
        dto.setPersonasMaximo(actividad.getPersonasMaximo());
        dto.setTotalPersonasInscritas(actividad.getTotalPersonasInscritas());
        dto.setOrganizacionId(actividad.getOrganizacion().getId());
        return dto;
    }
}