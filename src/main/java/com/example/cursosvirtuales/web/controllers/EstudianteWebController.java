package com.example.cursosvirtuales.web.controllers;

import com.example.cursosvirtuales.entities.Estudiante;
import com.example.cursosvirtuales.services.EstudianteService;
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
@RequestMapping("/estudiantes")
@Controller
public class EstudianteWebController {
	@Autowired
	private EstudianteService servicio;

	@GetMapping("/listar")
	public String getEstudiantes(Model model) {
		List<Estudiante> estudiantes = servicio.buscarTodo();
		model.addAttribute("listaEstudiantes", estudiantes);
		return "moduloEstudiante/listarEstudiantes";
	}

	@GetMapping("/agregar")
	public String nuevaEstudiante(Model model) {
		Estudiante estudiante = new Estudiante();
		model.addAttribute("estudiante", estudiante);
		return "/moduloEstudiante/nuevoEstudiante";
	}

	@PostMapping("/guardar")
	public String crearEstudiante(@Validated @ModelAttribute("estudiante") Estudiante estudiante, BindingResult result,
			RedirectAttributes flash) {
		if (estudiante.getIdEstudiante() != 0 && servicio.existe(estudiante.getIdEstudiante())) {
			if (result.hasErrors()) {
				List<String> mensajesError = new ArrayList<>();
				for (ObjectError error : result.getAllErrors()) {
					mensajesError.add(error.getDefaultMessage());
				}
				flash.addFlashAttribute("errores", mensajesError);
				// flash.addFlashAttribute("org.springframework.validation.BindingResult.estudiante", result);
				// flash.addFlashAttribute("estudiante", estudiante);
				return "redirect:/estudiantes/editar/" + estudiante.getIdEstudiante();
			}
			flash.addFlashAttribute("msgActualizado", "Estudiante actualizada correctamente.");
		} else {
			if (result.hasErrors()) {
				return "/moduloEstudiante/nuevoEstudiante";
			}
			flash.addFlashAttribute("msgAgregado", "Estudiante agregada correctamente.");
		}

		servicio.crear(estudiante);
		return "redirect:/estudiantes/listar";
	}

	@RequestMapping(value = "/editar/{id}", method = { RequestMethod.GET, RequestMethod.PUT })
	public ModelAndView editarEstudiante(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("/moduloEstudiante/editarEstudiante");
		Estudiante estudiante = servicio.buscarPorId(id);
		mav.addObject("estudiante", estudiante);
		mav.addObject("idEstudiante", id);
		return mav;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarEstudiante(@PathVariable("id") int id, RedirectAttributes flash) {
		try {
			servicio.eliminarEstudiante(id);
			flash.addFlashAttribute("success", "Estudiante eliminado correctamente.");
			return "redirect:/estudiantes/listar";
		} catch (DataIntegrityViolationException error) {
			flash.addFlashAttribute("error",
					"Este estudiante no se puede eliminar debido a restricciones de clave foránea.");
			System.out.println(error);
			return "redirect:/estudiantes/listar";
		}
	}
}
