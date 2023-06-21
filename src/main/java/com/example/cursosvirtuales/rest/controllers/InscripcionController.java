package com.example.cursosvirtuales.rest.controllers;

import com.example.cursosvirtuales.entities.Inscripcion;
import com.example.cursosvirtuales.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rest/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<Object> buscarTodo() {
        List<Inscripcion> listaInscripciones = inscripcionService.buscarTodo();
        return new ResponseEntity<Object>(listaInscripciones, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") int id) {
        Inscripcion inscripcion = inscripcionService.buscarPorId(id);
        if (inscripcion == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Inscripcion no encontrada,id porporcionado no es correcto");

        return new ResponseEntity<Object>(inscripcion, HttpStatus.OK);

    }


    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public  ResponseEntity <Object> crear (@RequestBody Inscripcion inscripcion){

        inscripcionService.crear(inscripcion);
        return new ResponseEntity<Object>("Inscripcion creada correctamente", HttpStatus.OK);

    }

    @PutMapping (value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Object> actualizar(@PathVariable("id") int id, @RequestBody Inscripcion inscripcion){

        inscripcionService.actualizar(inscripcion);
        return new ResponseEntity<Object>("Inscripcion actualizada correctamente", HttpStatus.OK);


    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") int id) {
        inscripcionService.eliminarInscripcion(id);
        return new ResponseEntity<Object>("Inscripcion eliminada correctamente", HttpStatus.OK);
    }

}
