package com.example.cursosvirtuales.web.controllers;

import com.example.cursosvirtuales.entities.Curso;
import com.example.cursosvirtuales.entities.Estudiante;
import com.example.cursosvirtuales.entities.Profesor;
import com.example.cursosvirtuales.services.CursoService;
import com.example.cursosvirtuales.services.ProfesorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Validated
@RequestMapping("/cursos")
@Controller
public class CursoWebController {
	@Autowired
	private CursoService servicio;

	@Autowired
	private ProfesorService servicioProfesor;

	@GetMapping("/listar")
	public String getCursos(Model model) {
		List<Curso> cursos = servicio.buscarTodo();
		model.addAttribute("listaCursos", cursos);
		return "moduloCurso/listarCursos";
	}

	@GetMapping("/agregar")
	public String nuevaCurso(Model model) {
		Curso curso = new Curso();
		model.addAttribute("curso", curso);

		List<Profesor> profesores = servicioProfesor.buscarTodo();
		model.addAttribute("listaProfesores", profesores);

		return "/moduloCurso/nuevoCurso";
	}

	@PostMapping("/guardar")
	public String crearCurso(@Validated @ModelAttribute("curso") Curso curso, BindingResult result,
			RedirectAttributes flash, @RequestParam("fechaInicio") String fechaInicioStr,
			@RequestParam("fechaFinalizacion") String fechaFinalizacionStr, Model model) {
		if (curso.getIdCurso() != 0 && servicio.existe(curso.getIdCurso())) {
			if (result.hasErrors()) {
				List<Profesor> profesores = servicioProfesor.buscarTodo();
				model.addAttribute("listaProfesores", profesores);

				List<String> mensajesError = new ArrayList<>();
				for (ObjectError error : result.getAllErrors()) {
					mensajesError.add(error.getDefaultMessage());
				}
				flash.addFlashAttribute("errores", mensajesError);
				// flash.addFlashAttribute("org.springframework.validation.BindingResult.curso",
				// result);
				// flash.addFlashAttribute("curso", curso);
				return "redirect:/cursos/editar/" + curso.getIdCurso();
			}
			flash.addFlashAttribute("msgActualizado", "Curso actualizado correctamente.");
		} else {
			List<Profesor> profesores = servicioProfesor.buscarTodo();
			model.addAttribute("listaProfesores", profesores);


			if (result.hasErrors()) {
				return "/moduloCurso/nuevoCurso";
			}
			flash.addFlashAttribute("msgAgregado", "Curso agregado correctamente.");
		}

		// Realizar conversión de tipo de fecha y hora
		LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr);
		curso.setFechaInicio(fechaInicio);

		LocalDateTime fechaFinalizacion = LocalDateTime.parse(fechaFinalizacionStr);
		curso.setFechaFinalizacion(fechaFinalizacion);

		servicio.crear(curso);
		return "redirect:/cursos/listar";
	}

	@RequestMapping(value = "/editar/{id}", method = { RequestMethod.GET, RequestMethod.PUT })
	public ModelAndView editarCurso(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("/moduloCurso/editarCurso");
		Curso curso = servicio.buscarPorId(id);
		mav.addObject("curso", curso);

		List<Profesor> profesores = servicioProfesor.buscarTodo();
		mav.addObject("listaProfesores", profesores);

		mav.addObject("idCurso", id);

		return mav;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarCurso(@PathVariable("id") int id, RedirectAttributes flash) {
		try {
			servicio.eliminarCurso(id);
			flash.addFlashAttribute("success", "Curso eliminado correctamente.");
			return "redirect:/cursos/listar";
		} catch (DataIntegrityViolationException error) {
			flash.addFlashAttribute("error",
					"Este curso no se puede eliminar debido a restricciones de clave foránea.");
			System.out.println(error);
			return "redirect:/cursos/listar";
		}
	}
}
