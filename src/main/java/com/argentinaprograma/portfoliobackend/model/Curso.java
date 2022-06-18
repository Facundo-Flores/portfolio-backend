package com.argentinaprograma.portfoliobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre_curso;
    private String institucion;
    private String carga_horaria;

    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy")
    private Date periodo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "logo")
    private byte[] logo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Persona persona;
}
