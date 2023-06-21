package com.example.cursosvirtuales.rest.controllers;

import com.example.cursosvirtuales.entities.Profesor;
import com.example.cursosvirtuales.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/rest/profesores")
@RestController
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping()
    public List<Profesor> buscarProfesor() {
        return profesorService.buscarTodo();
    }


    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") int id){
        Profesor profesor = profesorService.buscarPorId(id);
        if(profesor == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Profesor no encontrado,id porporcionado no es correcto");
        return new ResponseEntity<Object>(profesor, HttpStatus.OK);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public  ResponseEntity <Object> crear (@RequestBody Profesor profesor){

        profesorService.crear(profesor);
        return new ResponseEntity<Object>("Profesor creado correctamente", HttpStatus.OK);

    }

    @PutMapping (value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Object> actualizar(@PathVariable("id") int id, @RequestBody Profesor profesor){

        profesorService.actualizar(profesor);
        return new ResponseEntity<Object>("Profesor actualizado correctamente", HttpStatus.OK);


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") int id) {
        profesorService.eliminarProfesor(id);
        return new ResponseEntity<Object>("Profesor eliminado correctamente", HttpStatus.OK);
    }


}

