package com.argentinaprograma.portfoliobackend.response;

import com.argentinaprograma.portfoliobackend.model.Educacion;
import lombok.Data;

import java.util.List;

@Data
public class EducacionResponse {
    List<Educacion> educacionList;
}
