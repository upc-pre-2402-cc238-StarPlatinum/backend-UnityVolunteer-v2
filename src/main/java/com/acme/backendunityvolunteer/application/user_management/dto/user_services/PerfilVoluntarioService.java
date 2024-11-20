package com.acme.backendunityvolunteer.application.user_management.dto.user_services;

import com.acme.backendunityvolunteer.application.user_management.dto.PerfilVoluntarioDTO;
import com.acme.backendunityvolunteer.domain.user_management.model.PerfilVoluntario;
import com.acme.backendunityvolunteer.domain.user_management.model.TipoUsuario;
import com.acme.backendunityvolunteer.domain.user_management.model.Usuario;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.PerfilVoluntarioRepository;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PerfilVoluntarioService {

    @Autowired
    private PerfilVoluntarioRepository perfilVoluntarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PerfilVoluntarioDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
        PerfilVoluntario perfil = perfilVoluntarioRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            throw new RuntimeException("Perfil de voluntario no encontrado para el usuario con ID: " + usuarioId);
        }
        return mapToDTO(perfil);
    }

    // Método para actualizar un perfil de voluntario
    @Transactional
    public void actualizarPerfil(PerfilVoluntarioDTO perfilDTO) {
        PerfilVoluntario perfil = perfilVoluntarioRepository.findByUsuarioId(perfilDTO.getUsuarioId());
        if (perfil == null) {
            throw new RuntimeException("Perfil de voluntario no encontrado para el usuario con ID: " + perfilDTO.getUsuarioId());
        }

        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setExperiencia(perfilDTO.getExperiencia());
        perfil.setDisponibilidad(perfilDTO.getDisponibilidad());
        perfil.setPuntuacion(perfilDTO.getPuntuacion());

        perfilVoluntarioRepository.save(perfil);
    }

    @Transactional
    public void sumarPuntuacionActividad(PerfilVoluntario perfil, int puntuacionActividad) {

        if (perfil == null) {
            throw new RuntimeException("Error al sumar la puntacion de actividad con el perfil brindado");
        }

        int nuevaPuntuacion = perfil.getPuntuacion() + puntuacionActividad;
        perfil.setPuntuacion(nuevaPuntuacion);

        perfilVoluntarioRepository.save(perfil);
    }

    @Transactional
    public void crearPerfilVoluntario(PerfilVoluntarioDTO perfilDTO) {
        Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));

        if (usuario.getTipoUsuario() != TipoUsuario.VOLUNTARIO) {
            throw new IllegalArgumentException("El usuario con ID " + perfilDTO.getUsuarioId() + " no es un voluntario.");
        }

        PerfilVoluntario perfil = new PerfilVoluntario();
        perfil.setUsuario(usuario);
        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setExperiencia(perfilDTO.getExperiencia());
        perfil.setDisponibilidad(perfilDTO.getDisponibilidad());
        perfil.setPuntuacion(perfilDTO.getPuntuacion());

        perfilVoluntarioRepository.save(perfil);
    }


    // Método auxiliar para convertir PerfilVoluntario a PerfilVoluntarioDTO
    private PerfilVoluntarioDTO mapToDTO(PerfilVoluntario perfil) {
        PerfilVoluntarioDTO dto = new PerfilVoluntarioDTO();
        dto.setId(perfil.getId());
        dto.setUsuarioId(perfil.getUsuario().getId());
        dto.setIntereses(perfil.getIntereses());
        dto.setExperiencia(perfil.getExperiencia());
        dto.setDisponibilidad(perfil.getDisponibilidad());
        dto.setPuntuacion(perfil.getPuntuacion());
        return dto;
    }
}