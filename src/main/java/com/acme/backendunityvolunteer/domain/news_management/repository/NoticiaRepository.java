package com.acme.backendunityvolunteer.domain.news_management.repository;

import com.acme.backendunityvolunteer.domain.news_management.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    //Obtener todas las noticias publicadas por una organizacion especifica
    List<Noticia> findByOrganizacionId(Long organizacionId);
}

