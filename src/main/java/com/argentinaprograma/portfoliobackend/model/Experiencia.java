package com.argentinaprograma.portfoliobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "experiencia")
public class Experiencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String puesto;
    private String empresa;
    private String descripcion;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha_fin;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "logo")
    private byte[] logo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Persona persona;
}
