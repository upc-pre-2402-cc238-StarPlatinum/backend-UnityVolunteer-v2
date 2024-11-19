package com.acme.backendunityvolunteer.application.news_management.dto.news_services;

import com.acme.backendunityvolunteer.application.news_management.dto.NoticiaDTO;
import com.acme.backendunityvolunteer.domain.news_management.model.Noticia;
import com.acme.backendunityvolunteer.domain.news_management.repository.NoticiaRepository;
import com.acme.backendunityvolunteer.domain.user_management.model.PerfilOrganizacion;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.PerfilOrganizacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private PerfilOrganizacionRepository perfilOrganizacionRepository;

    //Metodo para crear una nueva notica
    @Transactional
    public NoticiaDTO crearNoticia(NoticiaDTO noticiaDTO) {
        PerfilOrganizacion organizacion = perfilOrganizacionRepository.findById(noticiaDTO.getOrganizacionId())
                .orElseThrow(() -> new RuntimeException("Organización no encontrada con ID: " + noticiaDTO.getOrganizacionId()));

        Noticia noticia = new Noticia();
        noticia.setTitulo(noticiaDTO.getTitulo());
        noticia.setDescripcion(noticiaDTO.getDescripcion());
        noticia.setFechaPublicacion(noticiaDTO.getFechaPublicacion());
        noticia.setOrganizacion(organizacion);

        noticiaRepository.save(noticia);
        return mapToDTO(noticia);
    }

    //Metodo para listar todas las noticias
    public List<NoticiaDTO> listarNoticias() {
        return noticiaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private NoticiaDTO mapToDTO(Noticia noticia) {
        NoticiaDTO dto = new NoticiaDTO();
        dto.setId(noticia.getId());
        dto.setTitulo(noticia.getTitulo());
        dto.setDescripcion(noticia.getDescripcion());
        dto.setFechaPublicacion(noticia.getFechaPublicacion());
        dto.setOrganizacionId(noticia.getOrganizacion().getId());

        return dto;
    }
}
