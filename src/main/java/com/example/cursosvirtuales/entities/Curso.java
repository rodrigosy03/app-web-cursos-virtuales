package com.example.cursosvirtuales.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
//@NamedQueries({
//		@NamedQuery(name = "Cursos.obtenerFecha", query = "SELECT DATE_FORMAT(c.fechaInicio, '%Y-%m-%d %H:%i') FROM cursos c"),
//		@NamedQuery(name = "Cursos.findProfesor", query = "SELECT p.nombre FROM cursos c JOIN c.profesor p WHERE c.idCurso = :idCurso")
//})

@Table(name = "cursos")
public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCurso")
	private int idCurso;

	@Column(name = "nombre")
	@NotBlank
	@Size(max = 50)
	private String nombre;

	@Column(name = "descripcion")
	@NotBlank
	@Size(max = 200)
	private String descripcion;
	
	@Column(name = "fechaInicio")
	private LocalDateTime fechaInicio;

	@Column(name = "fechaFinalizacion")
	@Future
	private LocalDateTime fechaFinalizacion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "curso", fetch = FetchType.LAZY)
	@JsonBackReference(value = "curso_insc")
	private List<Inscripcion> inscripcionList;

	@JoinColumn(name = "idProfesor", referencedColumnName = "idProfesor")
	@ManyToOne(optional = false)
	private Profesor profesor;
}
