package com.acme.backendunityvolunteer.application.user_management.dto.user_services;

import com.acme.backendunityvolunteer.application.user_management.dto.SesionUsuarioDTO;
import com.acme.backendunityvolunteer.domain.user_management.model.SesionUsuario;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.SesionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class SesionUsuarioService {
    @Autowired
    private SesionUsuarioRepository sesionUsuarioRepository;

    public List<SesionUsuarioDTO> obtenerSesionesPorUsuarioId(Long usuarioId) {
        List<SesionUsuario> sesiones = sesionUsuarioRepository.findByUsuarioId(usuarioId);
        return sesiones.stream().map(sesion -> {
            SesionUsuarioDTO dto = new SesionUsuarioDTO();
            dto.setId(sesion.getId());
            dto.setUsuarioId(sesion.getUsuario().getId());
            dto.setInicioSesion(sesion.getInicioSesion());
            dto.setDireccionIp(sesion.getDireccionIp());
            //dto.setExito(sesion.isExito());
            dto.setRazonFallo(sesion.getRazonFallo());
            dto.setTokenSesion(sesion.getTokenSesion());
            return dto;
        }).collect(Collectors.toList());
    }
}
