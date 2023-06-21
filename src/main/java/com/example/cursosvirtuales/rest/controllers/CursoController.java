package com.example.cursosvirtuales.rest.controllers;

import com.example.cursosvirtuales.entities.Curso;
import com.example.cursosvirtuales.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/rest/curso")
@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<Object> buscarTodo() {
        List<Curso> listaCursos = cursoService.buscarTodo();
        return new ResponseEntity<Object>(listaCursos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") int id) {
        Curso curso = cursoService.buscarPorId(id);
        if (curso == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Curso no encontrado, el ID proporcionado no es correcto");
        return new ResponseEntity<Object>(curso, HttpStatus.OK);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> crear(@RequestBody Curso curso) {
        cursoService.crear(curso);
        return new ResponseEntity<Object>("Curso creado correctamente", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> actualizar(@PathVariable("id") int id, @RequestBody Curso curso) {
        cursoService.actualizar(curso);
        return new ResponseEntity<Object>("Curso actualizado correctamente", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") int id) {
        cursoService.eliminarCurso(id);
        return new ResponseEntity<Object>("Curso eliminado correctamente", HttpStatus.OK);
    }
}
