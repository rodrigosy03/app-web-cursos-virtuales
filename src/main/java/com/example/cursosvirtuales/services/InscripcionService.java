package com.example.cursosvirtuales.services;

import com.example.cursosvirtuales.entities.Inscripcion;
import com.example.cursosvirtuales.repositories.InscripcionRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public Inscripcion crear(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public List<Inscripcion> buscarTodo() {
        return (ArrayList<Inscripcion>) inscripcionRepository.findAll();
    }

    public Inscripcion buscarPorId(Integer id) {
        return inscripcionRepository.findById(id).get();
    }

    public Inscripcion actualizar(Inscripcion inscripcionActualizar) {
        Inscripcion inscripcionActual = inscripcionRepository.findById(inscripcionActualizar.getIdInscripcion()).get();
        inscripcionActual.setIdInscripcion(inscripcionActualizar.getIdInscripcion());
        inscripcionActual.setEstudiante(inscripcionActualizar.getEstudiante());
        inscripcionActual.setCurso(inscripcionActualizar.getCurso());

        Inscripcion inscripcionActualizado = inscripcionRepository.save(inscripcionActual);
        return inscripcionActualizado;
    }

    public void eliminarInscripcion(Integer id) {
        inscripcionRepository.deleteById(id);
    }

    public boolean existe(Integer id){
        if (buscarPorId(id) != null){
            return true;
        }

        return false;
    }
}
