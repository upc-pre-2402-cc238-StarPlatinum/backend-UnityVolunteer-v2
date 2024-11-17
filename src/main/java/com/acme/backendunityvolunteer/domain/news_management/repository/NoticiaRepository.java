package com.acme.backendunityvolunteer.domain.news_management.repository;

import com.acme.backendunityvolunteer.domain.news_management.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    Optional<Noticia> findById(Long id);
    List<Noticia> findByActividadId(Long actividadId);
}

