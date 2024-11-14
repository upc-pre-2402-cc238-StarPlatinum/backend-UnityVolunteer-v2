package com.acme.backendunityvolunteer.infraestructure.activity_management.rest;

import com.acme.backendunityvolunteer.application.activity_management.dto.ActividadDTO;
import com.acme.backendunityvolunteer.application.activity_management.dto.VoluntarioInscritoDTO;
import com.acme.backendunityvolunteer.application.activity_management.dto.user_services.ActividadService;
import com.acme.backendunityvolunteer.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    // Crear una nueva actividad (solo la organización puede crear)
    @PostMapping("/organizacion/crear")
    public ResponseEntity<Map<String, Object>> crearActividad(@Valid @RequestBody ActividadDTO actividadDTO) {
        try {
            ActividadDTO nuevaActividad = actividadService.crearActividad(actividadDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("actividadId", nuevaActividad.getId());
            response.put("mensaje", "Actividad creada con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Organización no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al crear la actividad"));
        }
    }

    // Inscribir un voluntario en una actividad
    @PostMapping("/voluntario/participar/{actividadId}")
    public ResponseEntity<Map<String, String>> inscribirVoluntario(
            @PathVariable Long actividadId, @RequestParam Long voluntarioId) {
        try {
            actividadService.inscribirVoluntario(actividadId, voluntarioId);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Voluntario inscrito en la actividad con éxito");
            return ResponseEntity.ok(response);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Actividad o voluntario no encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al inscribir en la actividad"));
        }
    }

    // Listar todas las actividades (disponible para cualquier usuario)
    @GetMapping("/listar")
    public ResponseEntity<List<ActividadDTO>> listarActividades() {
        List<ActividadDTO> actividades = actividadService.listarActividades();
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/{actividadId}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesActividad(@PathVariable Long actividadId) {
        try {
            ActividadDTO actividad = actividadService.obtenerDetallesActividad(actividadId);

            Map<String, Object> response = new HashMap<>();
            response.put("actividad", actividad);
            response.put("voluntariosInscritos", actividadService.obtenerVoluntariosInscritos(actividadId));
            return ResponseEntity.ok(response);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Actividad no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al obtener los detalles de la actividad"));
        }
    }

    // Eliminar una actividad (solo la organización creadora puede eliminar)
    @DeleteMapping("/organizacion/eliminar/{actividadId}")
    public ResponseEntity<Map<String, String>> eliminarActividad(@PathVariable Long actividadId) {
        try {
            actividadService.eliminarActividad(actividadId);
            return ResponseEntity.ok(Map.of("mensaje", "Actividad eliminada con éxito"));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Actividad no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al eliminar la actividad"));
        }
    }


    // Obtener actividades creadas por una organización específica
    @GetMapping("/organizacion/{organizacionId}")
    public ResponseEntity<List<ActividadDTO>> obtenerActividadesPorOrganizacion(@PathVariable Long organizacionId) {
        try {
            List<ActividadDTO> actividades = actividadService.obtenerActividadesPorOrganizacion(organizacionId);
            return ResponseEntity.ok(actividades);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Obtener actividades en las que un voluntario está inscrito
    @GetMapping("/voluntario/{voluntarioId}")
    public ResponseEntity<List<ActividadDTO>> obtenerActividadesPorVoluntario(@PathVariable Long voluntarioId) {
        try {
            List<ActividadDTO> actividades = actividadService.obtenerActividadesPorVoluntario(voluntarioId);
            return ResponseEntity.ok(actividades);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    // Listar Voluntarios de una Actividad con Detalles Completos
    @GetMapping("/{actividadId}/ListarVoluntarios-Actividad")
    public ResponseEntity<List<VoluntarioInscritoDTO>> listarVoluntariosDeActividad(@PathVariable Long actividadId) {
        try {
            List<VoluntarioInscritoDTO> voluntarios = actividadService.listarVoluntariosDeActividad(actividadId);
            return ResponseEntity.ok(voluntarios);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }




}