package com.argentinaprograma.portfoliobackend.response;

import com.argentinaprograma.portfoliobackend.model.Persona;
import lombok.Data;

import java.util.List;

@Data
public class PersonaResponse {
    List<Persona> personas;
}
