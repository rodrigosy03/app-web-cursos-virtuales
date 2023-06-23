package com.example.cursosvirtuales.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Curso> cursos = servicio.buscarTodo();
        model.addAttribute("listaCursos", cursos);
        
        List<Profesor> profesores = servicioProfesor.buscarTodo();
        model.addAttribute("listaProfesores", profesores);
        
        return "home";
    }
	
	@GetMapping("/login")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión exitosamente.");
        }
        return "login";
    }

    @GetMapping("/integrantes")
    public String integrantes() {
        return "integrantes";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
