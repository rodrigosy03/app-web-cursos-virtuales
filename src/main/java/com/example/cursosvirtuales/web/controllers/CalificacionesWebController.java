package com.example.cursosvirtuales.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cursosvirtuales.entities.Calificacion;
import com.example.cursosvirtuales.entities.Estudiante;
import com.example.cursosvirtuales.entities.Curso;
import com.example.cursosvirtuales.services.CalificacionService;
import com.example.cursosvirtuales.services.EstudianteService;
import com.example.cursosvirtuales.services.CursoService;

import java.util.List;

@Validated
@RequestMapping("/calificaciones")
@Controller
public class CalificacionesWebController {
	@Autowired
    private CalificacionService servicio;
	
	@Autowired
	private CursoService servicioCurso;
	
	@Autowired
	private EstudianteService servicioEstudiante;
	
	@GetMapping("/listar")
    public String getCalificaciones(Model model) {
        List<Calificacion> calificaciones = servicio.buscarTodo();
        model.addAttribute("listaCalificaciones", calificaciones);
        return "moduloCalificacion/listarCalificaciones";
    }
    
    @GetMapping("/agregar")
	public String nuevaCalificacion(Model model) {
		Calificacion calificacion = new Calificacion();
		model.addAttribute("calificacion", calificacion);	
		
		List<Curso> cursos = servicioCurso.buscarTodo();
		model.addAttribute("listaCursos", cursos);
		
		List<Estudiante> estudiantes = servicioEstudiante.buscarTodo();
		model.addAttribute("listaEstudiantes", estudiantes);
		
		return "/moduloCalificacion/nuevaCalificacion";
	}

	@PostMapping("/guardar")
	public String crearCalificacion(@Validated @ModelAttribute("calificacion") Calificacion calificacion, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
	        return "/moduloCalificacion/nuevaCalificacion";
	    }
		
		flash.addFlashAttribute("msgAgregado", "Calificación agregada correctamente.");
		servicio.crear(calificacion);	    
		return "redirect:/calificaciones/listar";
	}

	@RequestMapping(value = "/editar/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
	public ModelAndView editarCalificacion(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("/moduloCalificacion/editarCalificacion");
		Calificacion calificacion = servicio.buscarPorId(id);
		mav.addObject("calificacion", calificacion);
		
		List<Curso> cursos = servicioCurso.buscarTodo();
		mav.addObject("listaCursos", cursos);
		
		List<Estudiante> estudiantes = servicioEstudiante.buscarTodo();
		mav.addObject("listaEstudiantes", estudiantes);
		
		return mav;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarCalificacion(@PathVariable("id") int id, RedirectAttributes flash) {
	    try {
	        servicio.eliminarCalificacion(id);
	        flash.addFlashAttribute("success", "Calificación eliminada correctamente.");
	        return "redirect:/calificaciones/listar";
	    } catch (DataIntegrityViolationException error) {
	        flash.addFlashAttribute("error", "Esta calificación no se puede eliminar debido a restricciones de clave foránea.");

	        return "redirect:/calificaciones/listar";
	    }
	}
}
