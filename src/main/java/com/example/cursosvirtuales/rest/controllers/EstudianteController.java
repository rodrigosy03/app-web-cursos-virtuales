package com.example.cursosvirtuales.rest.controllers;

import com.example.cursosvirtuales.entities.Estudiante;
import com.example.cursosvirtuales.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/rest/estudiantes")
@RestController
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<Object> buscarTodo() {
        List<Estudiante> listaEstudiante = estudianteService.buscarTodo();

        System.out.println("LISTA DE ESTUDIANTES : " + listaEstudiante);

        return  new ResponseEntity<>(listaEstudiante, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") int id) {
        Estudiante estudiante = estudianteService.buscarPorId(id);
        if (estudiante == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Estudiante no encontrada,id porporcionado no es correcto");
        return new ResponseEntity<Object>(estudiante, HttpStatus.OK);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public  ResponseEntity <Object> crear (@RequestBody Estudiante estudiante){

        estudianteService.crear(estudiante);
      return new ResponseEntity<Object>("Estudiante creado correctamente", HttpStatus.OK);

    }

    @PutMapping (value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Object> actualizar(@PathVariable("id") int id, @RequestBody Estudiante estudiante){

        estudianteService.actualizar(estudiante);
        return new ResponseEntity<Object>("Estudiante actualizado correctamente", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") int id) {
        estudianteService.eliminarEstudiante(id);
        return new ResponseEntity<Object>("Estudiante eliminado correctamente", HttpStatus.OK);
    }
}
