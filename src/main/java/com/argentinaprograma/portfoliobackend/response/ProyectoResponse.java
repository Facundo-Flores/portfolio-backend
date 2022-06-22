package com.argentinaprograma.portfoliobackend.response;

import com.argentinaprograma.portfoliobackend.model.Proyecto;
import lombok.Data;

import java.util.List;

@Data
public class ProyectoResponse {
    List<Proyecto> proyectos;
}
