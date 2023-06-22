package com.example.cursosvirtuales.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
@Table(name = "profesores")
@Entity
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesor")
    private int idProfesor;
    
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
