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
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 20, message = "El nombre debe tener como maximo 20 caracteres")
    private String nombre;

    @Column(name = "apellido")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido debe tener como maximo 50 caracteres")
    private String apellido;

    @Column(name = "email")
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;

    @Column(name = "contraseña")
    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
    private String contrasena;
}
