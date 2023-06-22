package com.example.cursosvirtuales.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Data
@Table(name = "calificaciones")
@Entity
@NamedQueries({
        @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c")
        , @NamedQuery(name = "Calificacion.findByIdCalificacion", query = "SELECT c FROM Calificacion c WHERE c.idCalificacion = :idCalificacion")
        , @NamedQuery(name = "Calificacion.findByNota", query = "SELECT c FROM Calificacion c WHERE c.nota = :nota")
        , @NamedQuery(name = "Calificacion.findByEstudiante", query = "SELECT c FROM Calificacion c WHERE c.estudiante = :estudiante")
})

public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCalificacion")
    private int idCalificacion;
    
    @Basic(optional = false)
    @Column(name = "nota")
    @NotNull
    @Min(value = 0)
    @Max(value = 20)
    private float nota;

    @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante estudiante;

    @JoinColumn(name = "idCurso", referencedColumnName = "idCurso")
    @ManyToOne(optional = false)
    private Curso curso;

}
