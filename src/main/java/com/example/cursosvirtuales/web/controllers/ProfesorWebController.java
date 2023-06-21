package com.example.cursosvirtuales.web.controllers;

import com.example.cursosvirtuales.entities.Profesor;
import com.example.cursosvirtuales.services.ProfesorService;

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

import java.util.List;

@RequestMapping("/profesores")
@Controller
public class ProfesorWebController {
	@Autowired
    private ProfesorService servicio;

    @GetMapping("/listar")
    public String getProfesores(Model model) {
        List<Profesor> profesores = servicio.buscarTodo();
        model.addAttribute("listaProfesores", profesores);
        return "moduloProfesor/listarProfesores";
    }
    
    @GetMapping("/agregar")
	public String nuevoProfesor(Model model) {
    	Profesor profesor = new Profesor();
		model.addAttribute("profesor", profesor);
		return "/moduloProfesor/nuevoProfesor";
	}

	@PostMapping("/guardar")
	public String crearProfesor(@ModelAttribute("profesor") Profesor profesor) {
		servicio.crear(profesor);
		return "redirect:/profesores/listar";

	}

	@RequestMapping(value = "/editar/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
	public ModelAndView editarProfesor(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("/moduloProfesor/editarProfesor");
		Profesor profesor = servicio.buscarPorId(id);
		mav.addObject("profesor", profesor);
		return mav;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarProfesor(@PathVariable("id") int id, RedirectAttributes flash) {
	    try {
	        servicio.eliminarProfesor(id);
	        flash.addFlashAttribute("success", "Profesor eliminado correctamente.");
	        return "redirect:/profesores/listar";
	    } catch (DataIntegrityViolationException error) {
	        flash.addFlashAttribute("error", "Este profesor no se puede eliminar debido a restricciones de clave foránea.");
	        System.out.println(error);
	        return "redirect:/profesores/listar";
	    }
	}
}
