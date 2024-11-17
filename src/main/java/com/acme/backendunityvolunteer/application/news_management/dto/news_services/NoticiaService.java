package com.acme.backendunityvolunteer.application.news_management.dto.news_services;

import com.acme.backendunityvolunteer.application.news_management.dto.NoticiaDTO;
import com.acme.backendunityvolunteer.domain.activity_management.repository.ActividadRepository;
import com.acme.backendunityvolunteer.domain.news_management.model.Noticia;
import com.acme.backendunityvolunteer.domain.news_management.repository.NoticiaRepository;
import com.acme.backendunityvolunteer.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    public NoticiaDTO publicarNoticia(NoticiaDTO noticiaDTO) {
        if(noticiaDTO.getActividadId() == null) {
            throw new IllegalArgumentException("Actividad ID must not be null");
        }

        Noticia nuevaNoticia = new Noticia();
        nuevaNoticia.setTitulo(noticiaDTO.getTitulo());
        nuevaNoticia.setDescripcion(noticiaDTO.getDescripcion());
        nuevaNoticia.setFechaPublicacion(noticiaDTO.getFechaPublicacion());
        nuevaNoticia.setActividad(actividadRepository.findById(noticiaDTO.getActividadId())
                .orElseThrow(() -> new NotFoundException("Actividad no encontrada")));

        Noticia noticiaGuardada = noticiaRepository.save(nuevaNoticia);
        return mapToDTO(noticiaGuardada);
    }

    public NoticiaDTO obtenerNoticiaPorId(Long noticiaId) {
        Noticia noticia = noticiaRepository.findById(noticiaId)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));
        return mapToDTO(noticia);
    }

    public List<NoticiaDTO> obtenerNoticiaPorActividadId(Long actividadId) {
        List<Noticia> noticias = noticiaRepository.findByActividadId(actividadId);
        if(noticias.isEmpty()) {
            throw new NotFoundException("No se encontraron noticias para la actividad con ID: " + actividadId);
        }
        return noticias.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private NoticiaDTO mapToDTO(Noticia noticia) {
        NoticiaDTO noticiaDTO = new NoticiaDTO();
        noticiaDTO.setId(noticia.getId());
        noticiaDTO.setTitulo(noticia.getTitulo());
        noticiaDTO.setDescripcion(noticia.getDescripcion());
        noticiaDTO.setFechaPublicacion(noticia.getFechaPublicacion());
        noticiaDTO.setActividadId(noticia.getActividad().getId());
        return noticiaDTO;
    }
}
