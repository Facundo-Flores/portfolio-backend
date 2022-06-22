package com.argentinaprograma.portfoliobackend.response;

import com.argentinaprograma.portfoliobackend.model.Curso;
import lombok.Data;

import java.util.List;

@Data
public class CursoResponse {
    List<Curso> cursos;
}
