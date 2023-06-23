package com.example.cursosvirtuales.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.cursosvirtuales.entities.Inscripcion;
import com.example.cursosvirtuales.entities.Estudiante;
import com.example.cursosvirtuales.entities.Curso;
import com.example.cursosvirtuales.services.InscripcionService;
import com.example.cursosvirtuales.services.EstudianteService;
import com.example.cursosvirtuales.services.CursoService;

import java.util.List;

@RequestMapping("/inscripciones")
@Controller
public class InscripcionesWebController {
	@Autowired
    private InscripcionService servicio;
	
	@Autowired
	private CursoService servicioCurso;
	
	@Autowired
	private EstudianteService servicioEstudiante;
	
	@GetMapping("/listar")
    public String getInscripciones(Model model) {
        List<Inscripcion> inscripciones = servicio.buscarTodo();
        model.addAttribute("listaInscripciones", inscripciones);
        return "moduloInscripcion/listarInscripciones";
    }
    
    @GetMapping("/agregar")
	public String nuevaInscripcion(Model model) {
		Inscripcion inscripcion = new Inscripcion();
		model.addAttribute("inscripcion", inscripcion);	
		
		List<Curso> cursos = servicioCurso.buscarTodo();
		model.addAttribute("listaCursos", cursos);
		
		List<Estudiante> estudiantes = servicioEstudiante.buscarTodo();
		model.addAttribute("listaEstudiantes", estudiantes);
		
		return "/moduloInscripcion/nuevaInscripcion";
	}

	@PostMapping("/guardar")
	public String crearInscripcion(@ModelAttribute("inscripcion") Inscripcion inscripcion, RedirectAttributes flash) {
		servicio.crear(inscripcion);
	    flash.addFlashAttribute("msgAgregado", "Calificación agregada correctamente.");
		return "redirect:/inscripciones/listar";
	}

	@RequestMapping(value = "/editar/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
	public ModelAndView editarInscripcion(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("/moduloInscripcion/editarInscripcion");
		Inscripcion inscripcion = servicio.buscarPorId(id);
		mav.addObject("inscripcion", inscripcion);
		
		List<Curso> cursos = servicioCurso.buscarTodo();
		mav.addObject("listaCursos", cursos);
		
		List<Estudiante> estudiantes = servicioEstudiante.buscarTodo();
		mav.addObject("listaEstudiantes", estudiantes);
		
		return mav;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarInscripcion(@PathVariable("id") int id, RedirectAttributes flash) {
	    try {
	        servicio.eliminarInscripcion(id);
	        flash.addFlashAttribute("success", "Calificación eliminado correctamente.");
	        return "redirect:/inscripciones/listar";
	    } catch (DataIntegrityViolationException error) {
	        flash.addFlashAttribute("error", "Esta calificación no se puede eliminar debido a restricciones de clave foránea.");

	        return "redirect:/inscripciones/listar";
	    }
	}
}
