package com.argentinaprograma.portfoliobackend.response;

import com.argentinaprograma.portfoliobackend.model.Experiencia;
import lombok.Data;

import java.util.List;

@Data
public class ExperienciaResponse {
    List<Experiencia> experiencias;
}
