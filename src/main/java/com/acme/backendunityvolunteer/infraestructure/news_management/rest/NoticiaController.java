package com.acme.backendunityvolunteer.infraestructure.news_management.rest;

import com.acme.backendunityvolunteer.application.activity_management.dto.user_services.ActividadService;
import com.acme.backendunityvolunteer.application.news_management.dto.NoticiaDTO;
import com.acme.backendunityvolunteer.application.news_management.dto.news_services.NoticiaService;
import com.acme.backendunityvolunteer.infraestructure.news_management.rest.dto.NoticiaRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticia")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private ActividadService actividadService;

    // -------------------
    // Gestión de Noticias
    // -------------------

    @PostMapping("/Publicar")
    public ResponseEntity<String> PublicarNoticia(@Valid @RequestBody NoticiaRequest request) {
        // Crear y guardar la noticia primero
        NoticiaDTO noticiaDTO = new NoticiaDTO();
        noticiaDTO.setTitulo(request.getTitulo());
        noticiaDTO.setDescripcion(request.getDescripcion());
        noticiaDTO.setFechaPublicacion(request.getFechaPublicacion());
        noticiaDTO.setActividadId(request.getActividadId());

        // Guardar la noticia en la base de datos
        NoticiaDTO noticiaGuardada = noticiaService.publicarNoticia(noticiaDTO);
        return ResponseEntity.ok("Noticia publicada con éxito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticiaDTO> ObtenerNoticiaPorId(@PathVariable Long id) {
        NoticiaDTO noticia = noticiaService.obtenerNoticiaPorId(id);
        return ResponseEntity.ok(noticia);
    }

    @GetMapping("/Actividad/{id}")
    public ResponseEntity<List<NoticiaDTO>> ObtenerNoticiasPorActividadId(@PathVariable Long id) {
        List<NoticiaDTO> noticias = noticiaService.obtenerNoticiaPorActividadId(id);
        return ResponseEntity.ok(noticiaService.obtenerNoticiaPorActividadId(id));
    }
}