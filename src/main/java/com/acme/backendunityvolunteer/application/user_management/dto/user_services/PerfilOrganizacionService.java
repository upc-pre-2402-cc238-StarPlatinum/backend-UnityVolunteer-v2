package com.acme.backendunityvolunteer.application.user_management.dto.user_services;

import com.acme.backendunityvolunteer.application.user_management.dto.PerfilOrganizacionDTO;
import com.acme.backendunityvolunteer.domain.user_management.model.PerfilOrganizacion;
import com.acme.backendunityvolunteer.domain.user_management.model.Usuario;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.PerfilOrganizacionRepository;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerfilOrganizacionService {

    @Autowired
    private PerfilOrganizacionRepository perfilOrganizacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener el perfil de organización por ID de usuario
    public PerfilOrganizacionDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
        PerfilOrganizacion perfil = perfilOrganizacionRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            throw new RuntimeException("Perfil de organización no encontrado para el usuario con ID: " + usuarioId);
        }

        return mapToDTO(perfil);
    }

    // Método para actualizar un perfil de organización
    @Transactional
    public void actualizarPerfil(PerfilOrganizacionDTO perfilDTO) {
        PerfilOrganizacion perfil = perfilOrganizacionRepository.findByUsuarioId(perfilDTO.getUsuarioId());
        if (perfil == null) {
            throw new RuntimeException("Perfil de organización no encontrado para el usuario con ID: " + perfilDTO.getUsuarioId());
        }

        // Actualizar los campos del perfil
        perfil.setNombreOrganizacion(perfilDTO.getNombreOrganizacion());
        perfil.setTipoOrganizacion(perfilDTO.getTipoOrganizacion());
        perfil.setSitioWeb(perfilDTO.getSitioWeb());

        perfilOrganizacionRepository.save(perfil);
    }

    @Transactional
    public void crearPerfilOrganizacion(PerfilOrganizacionDTO perfilDTO) {

        if (perfilDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));

        PerfilOrganizacion perfil = new PerfilOrganizacion();
        // Asignamos el usuario al perfil
        perfil.setUsuario(usuario);

        perfil.setNombreOrganizacion(perfilDTO.getNombreOrganizacion() != null ? perfilDTO.getNombreOrganizacion() : "Organización sin nombre");
        perfil.setTipoOrganizacion(perfilDTO.getTipoOrganizacion() != null ? perfilDTO.getTipoOrganizacion() : "Sin definir");
        perfil.setSitioWeb(perfilDTO.getSitioWeb() != null ? perfilDTO.getSitioWeb() : "Sin sitio web");
        // Guardamos el perfil en la base de datos
        perfilOrganizacionRepository.save(perfil);
    }


    // Método auxiliar para convertir PerfilOrganizacion a PerfilOrganizacionDTO
    private PerfilOrganizacionDTO mapToDTO(PerfilOrganizacion perfil) {
        PerfilOrganizacionDTO dto = new PerfilOrganizacionDTO();
        dto.setId(perfil.getId());
        dto.setUsuarioId(perfil.getUsuario().getId());
        dto.setNombreOrganizacion(perfil.getNombreOrganizacion());
        dto.setTipoOrganizacion(perfil.getTipoOrganizacion());
        dto.setSitioWeb(perfil.getSitioWeb());
        return dto;
    }
}