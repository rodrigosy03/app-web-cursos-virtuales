package com.example.cursosvirtuales.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.cursosvirtuales.entities.Curso;
import com.example.cursosvirtuales.entities.Profesor;
import com.example.cursosvirtuales.services.CursoService;
import com.example.cursosvirtuales.services.ProfesorService;

@RequestMapping
@Controller
public class HomeWebController {
	@Autowired
    private CursoService servicio;
	
	@Autowired
	private ProfesorService servicioProfesor;
	
	@GetMapping("/")
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		
        List<Curso> cursos = servicio.buscarTodo();
        model.addAttribute("listaCursos", cursos);
        
        List<Profesor> profesores = servicioProfesor.buscarTodo();
        model.addAttribute("listaProfesores", profesores);
        
        return "home";
    }
}
