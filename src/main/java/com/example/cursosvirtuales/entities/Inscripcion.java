package com.example.cursosvirtuales.entities;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Table(name = "inscripciones")
@Entity
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInscripcion")
    private int idInscripcion;

    @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante estudiante;

    @JoinColumn(name = "idCurso", referencedColumnName = "idCurso")
    @ManyToOne(optional = false)
    private Curso curso;

}
