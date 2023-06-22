package com.example.cursosvirtuales.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
@Table(name = "estudiantes")
@Entity
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstudiante")
    private int idEstudiante;

    @Column(name = "nombre")
    @NotBlank
    @Size(max = 20)
    private String nombre;

    @Column(name = "apellido")
    @NotBlank
    @Size(max = 50)
    private String apellido;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "contraseña")
    @NotBlank
    @Size(min = 6)
    private String contrasena;
}
