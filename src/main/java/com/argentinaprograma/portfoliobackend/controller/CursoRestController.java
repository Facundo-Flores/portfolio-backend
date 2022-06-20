package com.argentinaprograma.portfoliobackend.controller;

import com.argentinaprograma.portfoliobackend.model.Curso;
import com.argentinaprograma.portfoliobackend.response.CursoResponse;
import com.argentinaprograma.portfoliobackend.service.curso.CursoServiceImpl;
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
public class CursoRestController {

    private final CursoServiceImpl cursoService;

    public CursoRestController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/curso")
    public ResponseEntity<CursoResponse> buscar() {
        return cursoService.buscar();
    }

    @GetMapping("/curso/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @PostMapping("/curso")
    public ResponseEntity<CursoResponse> crear(
            @RequestParam("logo") @Nullable MultipartFile logo,
            @RequestParam("nombre") String nombre,
            @RequestParam("institucion") String institucion,
            @RequestParam("carga_horaria") String carga_horaria,
            @RequestParam("periodo") @Nullable @DateTimeFormat(pattern = "yyyy") Date periodo
            //@RequestParam("personaId") Long personaId
    ) throws IOException {

        Curso curso = new Curso();
        curso.setNombre_curso(nombre);
        curso.setInstitucion(institucion);
        curso.setCarga_horaria(carga_horaria);
        curso.setPeriodo(periodo);
        if (logo != null)
            curso.setLogo(ImageUtility.compressZLib(logo.getBytes()));
        return cursoService.crear(curso, 1L);
    }

    @PutMapping("/curso/{id}")
    public ResponseEntity<CursoResponse> editar(
            @RequestParam("logo") @Nullable MultipartFile logo,
            @RequestParam("nombre") String nombre,
            @RequestParam("institucion") String institucion,
            @RequestParam("carga_horaria") String carga_horaria,
            @RequestParam("periodo") @Nullable @DateTimeFormat(pattern = "yyyy") Date periodo,
            @PathVariable Long id
    ) throws IOException {

        Curso curso = new Curso();
        curso.setNombre_curso(nombre);
        curso.setInstitucion(institucion);
        curso.setCarga_horaria(carga_horaria);
        curso.setPeriodo(periodo);
        if (logo != null)
            curso.setLogo(ImageUtility.compressZLib(logo.getBytes()));
        return cursoService.editar(curso, id);
    }

    @DeleteMapping("/curso/{id}")
    public ResponseEntity<CursoResponse> borrar(@PathVariable Long id) {
        return cursoService.borrar(id);
    }
}
