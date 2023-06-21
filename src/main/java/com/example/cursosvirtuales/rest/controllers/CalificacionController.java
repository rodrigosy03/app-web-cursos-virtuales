package com.example.cursosvirtuales.rest.controllers;

import com.example.cursosvirtuales.entities.Calificacion;
import com.example.cursosvirtuales.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequestMapping("/rest/calificaciones")
@RestController
public class CalificacionController {
    @Autowired
    private CalificacionService calificacionService;

    @GetMapping
    public ResponseEntity<Object> buscarTodo() {
        List<Calificacion> listaCalificaciones = calificacionService.buscarTodo();
        return new ResponseEntity<Object>(listaCalificaciones, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") int id) {
        Calificacion calificacion = calificacionService.buscarPorId(id);
        if (calificacion == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Calificación no encontrada, id porporcionado no es correcto");
        return new ResponseEntity<Object>(calificacion, HttpStatus.OK);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> crear(@RequestBody Calificacion calificacion) {
        calificacionService.crear(calificacion);
        return new ResponseEntity<Object>("Calificación creada correctamente", HttpStatus.OK);

    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> actualizar(@PathVariable("id") int id, @RequestBody Calificacion calificacion) {
        calificacionService.actualizar(calificacion);
        return new ResponseEntity<Object>("Calificación actualizada correctamente", HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") int id) {
        calificacionService.eliminarCalificacion(id);
        return new ResponseEntity<Object>("Calificacion eliminada correctamente", HttpStatus.OK);
    }
}
