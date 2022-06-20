package com.argentinaprograma.portfoliobackend.controller;

import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.response.PersonaResponse;
import com.argentinaprograma.portfoliobackend.service.persona.PersonaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PersonaRestController {

    private final PersonaServiceImpl service;

    public PersonaRestController(PersonaServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/persona")
    public ResponseEntity<PersonaResponse> buscarPersonas() {
        return service.buscar();
    }

    @GetMapping("/persona/{id}")
    public ResponseEntity<PersonaResponse> buscarPersonaPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/persona")
    public ResponseEntity<PersonaResponse> crear(@RequestBody Persona persona) {
        return service.crear(persona);
    }

    @PutMapping("/persona/{id}")
    public ResponseEntity<PersonaResponse> actualizar(@RequestBody Persona persona, @PathVariable Long id) {
        return service.editar(persona, id);
    }

    @DeleteMapping("/persona/{id}")
    public ResponseEntity<PersonaResponse> borrar(@PathVariable Long id) {
        return service.borrar(id);
    }
}
