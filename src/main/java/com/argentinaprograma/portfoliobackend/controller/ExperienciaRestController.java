package com.argentinaprograma.portfoliobackend.controller;

import com.argentinaprograma.portfoliobackend.model.Experiencia;
import com.argentinaprograma.portfoliobackend.response.ExperienciaResponse;
import com.argentinaprograma.portfoliobackend.service.ICurriculumService;
import com.argentinaprograma.portfoliobackend.service.experiencia.ExperienciaServiceImpl;
import com.argentinaprograma.portfoliobackend.util.ImageUtility;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ExperienciaRestController {

    private final ExperienciaServiceImpl experienciaService;

    public ExperienciaRestController(ExperienciaServiceImpl experienciaService) {
        this.experienciaService = experienciaService;
    }

    @GetMapping("/experiencia")
    public ResponseEntity<ExperienciaResponse> buscarExperiencias() {
        return experienciaService.buscar();
    }

    @GetMapping("/experiencia/{id}")
    public ResponseEntity<ExperienciaResponse> buscarExperienciaPorId(@PathVariable Long id) {
        return experienciaService.buscarPorId(id);
    }

    @PostMapping("/experiencia")
    public ResponseEntity<ExperienciaResponse> crear(
            @RequestParam("logo") @Nullable MultipartFile logo,
            @RequestParam("puesto") String puesto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("empresa") String empresa,
            @RequestParam("fecha_inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_inicio,
            @RequestParam("fecha_fin") @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin
    ) throws IOException, ParseException {

        Experiencia experiencia = new Experiencia();
        experiencia.setPuesto(puesto);
        experiencia.setDescripcion(descripcion);
        experiencia.setEmpresa(empresa);
        experiencia.setFecha_inicio(fecha_inicio);
        experiencia.setFecha_fin(fecha_fin);
        if (logo != null) {
            experiencia.setLogo(ImageUtility.compressZLib(logo.getBytes()));
        }
        return experienciaService.crear(experiencia, 1L);
    }

    @PutMapping ("/experiencia/{id}")
    public ResponseEntity<ExperienciaResponse> editar(
            @RequestParam("logo") @Nullable MultipartFile logo,
            @RequestParam("puesto") String puesto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("empresa") String empresa,
            @RequestParam("fecha_inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_inicio,
            @RequestParam("fecha_fin") @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin,
            @PathVariable Long id
    ) throws IOException {

        Experiencia experiencia = new Experiencia();
        experiencia.setPuesto(puesto);
        experiencia.setDescripcion(descripcion);
        experiencia.setEmpresa(empresa);
        experiencia.setFecha_inicio(fecha_inicio);
        experiencia.setFecha_fin(fecha_fin);
        if (logo != null)
            experiencia.setLogo(ImageUtility.compressZLib(logo.getBytes()));
        return experienciaService.editar(experiencia, id);
    }

    @DeleteMapping("/experiencia/{id}")
    public ResponseEntity<ExperienciaResponse> borrar(@PathVariable Long id) {
        return experienciaService.borrar(id);
    }
}
