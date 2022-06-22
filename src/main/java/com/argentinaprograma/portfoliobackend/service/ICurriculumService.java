package com.argentinaprograma.portfoliobackend.service;

import org.springframework.http.ResponseEntity;

public interface ICurriculumService<T> {

    ResponseEntity buscar();

    ResponseEntity buscarPorId(Long id);

    ResponseEntity crear(T objeto, Long personaId);

    ResponseEntity crear(T objeto);

    ResponseEntity editar(T objeto, Long id);

    ResponseEntity borrar(Long id);
}
