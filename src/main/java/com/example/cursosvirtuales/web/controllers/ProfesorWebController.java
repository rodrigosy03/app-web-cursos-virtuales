package com.example.cursosvirtuales.web.controllers;

import com.example.cursosvirtuales.entities.Profesor;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Validated
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
	public String crearProfesor(@Validated @ModelAttribute("profesor") Profesor profesor, BindingResult result, RedirectAttributes flash) {
		if (profesor.getIdProfesor() != 0 && servicio.existe(profesor.getIdProfesor())) {
			if (result.hasErrors()) {
				List<String> mensajesError = new ArrayList<>();
				for (ObjectError error : result.getAllErrors()) {
					mensajesError.add(error.getDefaultMessage());
				}
				flash.addFlashAttribute("errores", mensajesError);
				// flash.addFlashAttribute("org.springframework.validation.BindingResult.profesor", result);
				// flash.addFlashAttribute("estudiante", estudiante);
				return "redirect:/profesores/editar/" + profesor.getIdProfesor();
			}
			flash.addFlashAttribute("msgActualizado", "Profesor actualizado correctamente.");
		} else {
			if (result.hasErrors()) {
				return "/moduloProfesor/nuevoProfesor";
			}
			flash.addFlashAttribute("msgAgregado", "Profesor agregado correctamente.");
		}

		servicio.crear(profesor);
		return "redirect:/profesores/listar";
	}

	@RequestMapping(value = "/editar/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
	public ModelAndView editarProfesor(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("/moduloProfesor/editarProfesor");
		Profesor profesor = servicio.buscarPorId(id);
		mav.addObject("profesor", profesor);
		mav.addObject("idProfesor", id);
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
