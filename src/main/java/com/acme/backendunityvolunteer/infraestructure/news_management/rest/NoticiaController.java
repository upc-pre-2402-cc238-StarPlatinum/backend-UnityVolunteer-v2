package com.acme.backendunityvolunteer.infraestructure.news_management.rest;

import com.acme.backendunityvolunteer.application.news_management.dto.NoticiaDTO;
import com.acme.backendunityvolunteer.application.news_management.dto.news_services.NoticiaService;
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
@RequestMapping("/api/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    // Crear una nueva noticia
    @PostMapping("/organizacion/crear")
    public ResponseEntity<Map<String, Object>> crearNoticia(@Valid @RequestBody NoticiaDTO noticiaDTO) {
        try {
            NoticiaDTO nuevaNoticia = noticiaService.crearNoticia(noticiaDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("noticiaId", nuevaNoticia.getId());
            response.put("mensaje", "Noticia creada con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Organización no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al crear la noticia"));
        }
    }

    // Listar todas las noticias
    @GetMapping("/listar")
    public ResponseEntity<List<NoticiaDTO>> listarNoticias() {
        List<NoticiaDTO> noticias = noticiaService.listarNoticias();
        return ResponseEntity.ok(noticias);
    }

    //Obtener todas las noticias publicadas por una organizacion
    @GetMapping("/organizacion/{organizacionId}")
    public ResponseEntity<List<NoticiaDTO>> obtenerNoticiasPorOrganizacion(@PathVariable Long organizacionId) {
        try {
            List<NoticiaDTO> noticias = noticiaService.obtenerNoticiasPorOrganizacion(organizacionId);
            return ResponseEntity.ok(noticias);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    //Eliminar una noticia
    @DeleteMapping("/organizacion/eliminar/{noticiaId}")
    public ResponseEntity<Map<String, String>> eliminarNoticia(@PathVariable Long noticiaId) {
        try {
            noticiaService.eliminarNoticia(noticiaId);
            return ResponseEntity.ok(Map.of("mensaje", "Noticia eliminada con éxito"));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Noticia no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al eliminar la noticia"));
        }
    }
}