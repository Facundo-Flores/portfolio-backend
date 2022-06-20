package com.argentinaprograma.portfoliobackend.controller;

import com.argentinaprograma.portfoliobackend.model.Educacion;
import com.argentinaprograma.portfoliobackend.response.EducacionResponse;
import com.argentinaprograma.portfoliobackend.service.educacion.EducacionServiceImpl;
import com.argentinaprograma.portfoliobackend.util.ImageUtility;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class EducacionRestController {

    private final EducacionServiceImpl educacionService;

    public EducacionRestController(EducacionServiceImpl educacionService) {
        this.educacionService = educacionService;
    }

    @GetMapping("/educacion")
    public ResponseEntity<EducacionResponse> buscar() {
        return educacionService.buscar();
    }

    @GetMapping("/educacion/{id}")
    public ResponseEntity<EducacionResponse> buscarPorId(@PathVariable Long id) {
        return educacionService.buscarPorId(id);
    }

    @PostMapping("/educacion")
    public ResponseEntity<EducacionResponse> crear(
            @RequestParam("logo") @Nullable MultipartFile logo,
            @RequestParam("titulo") String titulo,
            @RequestParam("institucion") String institucion,
            @RequestParam("fecha_inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_inicio,
            @RequestParam("fecha_fin") @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin
            //@RequestParam("personaId") Long personaId
    ) throws IOException {

        Educacion educacion = new Educacion();
        educacion.setTitulo(titulo);
        educacion.setInstitucion(institucion);
        educacion.setFecha_inicio(fecha_inicio);
        educacion.setFecha_fin(fecha_fin);
        if (logo != null)
            educacion.setLogo(ImageUtility.compressZLib(logo.getBytes()));
        return educacionService.crear(educacion, 1L);
    }

    @PutMapping("/educacion/{id}")
    public ResponseEntity<EducacionResponse> editar(
            @RequestParam("logo") @Nullable MultipartFile logo,
            @RequestParam("titulo") String titulo,
            @RequestParam("institucion") String institucion,
            @RequestParam("fecha_inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_inicio,
            @RequestParam("fecha_fin") @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin,
            @PathVariable Long id
    ) throws IOException {

        Educacion educacion = new Educacion();
        educacion.setTitulo(titulo);
        educacion.setInstitucion(institucion);
        educacion.setFecha_inicio(fecha_inicio);
        educacion.setFecha_fin(fecha_fin);
        if (logo != null)
            educacion.setLogo(ImageUtility.compressZLib(logo.getBytes()));
        return educacionService.editar(educacion, id);
    }

    @DeleteMapping("/educacion/{id}")
    public ResponseEntity<EducacionResponse> borrar(@PathVariable Long id) {
        return educacionService.borrar(id);
    }
}
