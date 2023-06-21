    package com.example.cursosvirtuales.web.controllers;

    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;

    @Controller
    public class ErrorWebController {
        @RequestMapping("/custom-error")
        public String error(Model model) {
            model.addAttribute("errorMessage", "No tienes permiso para realizar esta acción.");
            return "error";
        }
    }
