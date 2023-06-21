package com.example.cursosvirtuales.entities;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(name = "profesores")
@Entity
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idProfesor", nullable = false)
    private int idProfesor;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "email")
    private String email;
    @Column(name = "contraseña")
    private String contrasena;
}
