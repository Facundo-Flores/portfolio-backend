package com.argentinaprograma.portfoliobackend.response;

import com.argentinaprograma.portfoliobackend.model.Habilidad;
import lombok.Data;

import java.util.List;

@Data
public class HabilidadResponse {
    List<Habilidad> habilidades;
}
