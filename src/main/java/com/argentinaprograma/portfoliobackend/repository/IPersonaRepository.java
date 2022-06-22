package com.argentinaprograma.portfoliobackend.repository;

import com.argentinaprograma.portfoliobackend.model.Persona;
import org.springframework.data.repository.CrudRepository;

public interface IPersonaRepository extends CrudRepository<Persona, Long> {
}
