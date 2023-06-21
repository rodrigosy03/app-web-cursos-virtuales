package com.example.cursosvirtuales.entities;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Entity
@Table(name = "estudiantes")
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "idEstudiante")
    private int idEstudiante;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email")
    private String email;

    @Column(name = "contraseña")
    private String contrasena;


}
