package com.argentinaprograma.portfoliobackend.controller;

import com.argentinaprograma.portfoliobackend.model.Proyecto;
import com.argentinaprograma.portfoliobackend.response.ProyectoResponse;
import com.argentinaprograma.portfoliobackend.service.proyecto.ProyectoServiceImpl;
import com.argentinaprograma.portfoliobackend.util.ImageUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ProyectoRestController {

    private final ProyectoServiceImpl proyectoService;

    public ProyectoRestController(ProyectoServiceImpl proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping("/proyecto")
    public ResponseEntity<ProyectoResponse> buscarProyectos() {
        return proyectoService.buscar();
    }

    @GetMapping("/proyecto/{id}")
    public ResponseEntity<ProyectoResponse> buscarProyectoPorId(@PathVariable Long id) {
        return proyectoService.buscarPorId(id);
    }

    @PostMapping("/proyecto")
    public ResponseEntity<ProyectoResponse> crear(
            @RequestParam("img") @Nullable MultipartFile img,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("url") String url
            //@RequestParam("personaId") Long personaId
    ) throws IOException {

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setUrl(url);
        if (img != null)
            proyecto.setImg(ImageUtility.compressZLib(img.getBytes()));
        return proyectoService.crear(proyecto, 1L);
    }

    @PutMapping("/proyecto/{id}")
    public ResponseEntity<ProyectoResponse> editar(
            @RequestParam("img") @Nullable MultipartFile img,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("url") String url,
            @PathVariable Long id
    ) throws IOException {

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setUrl(url);
        if (img != null)
            proyecto.setImg(ImageUtility.compressZLib(img.getBytes()));
        return proyectoService.editar(proyecto, id);
    }

    @DeleteMapping("/proyecto/{id}")
    public ResponseEntity<ProyectoResponse> borrar(@PathVariable Long id) {
        return proyectoService.borrar(id);
    }
}
