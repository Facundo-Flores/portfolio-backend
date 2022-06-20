package com.argentinaprograma.portfoliobackend.controller;

import com.argentinaprograma.portfoliobackend.model.Habilidad;
import com.argentinaprograma.portfoliobackend.response.HabilidadResponse;
import com.argentinaprograma.portfoliobackend.service.habilidad.HabilidadServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class HabilidadRestController {

    private final HabilidadServiceImpl habilidadService;

    public HabilidadRestController(HabilidadServiceImpl habilidadService) {
        this.habilidadService = habilidadService;
    }

    @GetMapping("/habilidad")
    public ResponseEntity<HabilidadResponse> buscar() {
        return habilidadService.buscar();
    }

    @GetMapping("/habilidad/{id}")
    public ResponseEntity<HabilidadResponse> buscarPorId(@PathVariable Long id) {
        return habilidadService.buscarPorId(id);
    }

    @PostMapping("/habilidad")
    public ResponseEntity<HabilidadResponse> crear(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("nivel") String nivel
    ) {
        Habilidad habilidad = new Habilidad();
        habilidad.setNombre(nombre);
        habilidad.setDescripcion(descripcion);
        habilidad.setNivel(nivel);

        return habilidadService.crear(habilidad, 1L);
    }

    @PutMapping("/habilidad/{id}")
    public ResponseEntity<HabilidadResponse> editar(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("nivel") String nivel,
            @PathVariable Long id
    ) {
        Habilidad habilidad = new Habilidad();
        habilidad.setNombre(nombre);
        habilidad.setDescripcion(descripcion);
        habilidad.setNivel(nivel);

        return habilidadService.editar(habilidad, id);
    }

    @DeleteMapping("/habilidad/{id}")
    public ResponseEntity<HabilidadResponse> borrar(@PathVariable Long id) {
        return habilidadService.borrar(id);
    }
}
