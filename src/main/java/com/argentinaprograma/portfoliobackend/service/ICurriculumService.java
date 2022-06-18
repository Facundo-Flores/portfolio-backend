package com.argentinaprograma.portfoliobackend.service;

import com.argentinaprograma.portfoliobackend.model.Curso;
import com.argentinaprograma.portfoliobackend.response.CursoResponse;
import org.springframework.http.ResponseEntity;

public interface ICurriculumService {

    ResponseEntity buscar();

    ResponseEntity buscarPorId(Long id);

    ResponseEntity crear(Curso curso, Long personaId);

    ResponseEntity editar(Curso curso, Long id);

    ResponseEntity borrar(Long id);
}
