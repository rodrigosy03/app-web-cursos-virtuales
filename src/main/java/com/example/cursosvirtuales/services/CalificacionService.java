package com.example.cursosvirtuales.services;

import com.example.cursosvirtuales.entities.Calificacion;
import com.example.cursosvirtuales.repositories.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CalificacionService {

    @Autowired
    public CalificacionRepository calificacionRepository;

    public Calificacion crear(Calificacion calificaciones) {
        return calificacionRepository.save(calificaciones);
    }

    public List<Calificacion> buscarTodo() {
        return (ArrayList<Calificacion>) calificacionRepository.findAll();
    }

    public Calificacion buscarPorId(Integer id) {
        return calificacionRepository.findById(id).get();
    }

    public Calificacion actualizar(Calificacion calificacionesActualizar) {

        Calificacion calificacionActual = calificacionRepository.findById(calificacionesActualizar.getIdCalificacion()).get();

        calificacionActual.setIdCalificacion(calificacionesActualizar.getIdCalificacion());
        calificacionActual.setEstudiante(calificacionesActualizar.getEstudiante());
        calificacionActual.setIdCalificacion(calificacionesActualizar.getIdCalificacion());
        calificacionActual.setNota(calificacionesActualizar.getNota());

        Calificacion funcionesActualizado = calificacionRepository.save(calificacionActual);
        return funcionesActualizado;
    }

    public void eliminarCalificacion(Integer id) {
        calificacionRepository.deleteById(id);
    }

    public void actualizar(int id, Calificacion calificacion) {

        Calificacion calificacionActual = calificacionRepository.findById(id).get();

        calificacionActual.setIdCalificacion(calificacion.getIdCalificacion());
        calificacionActual.setEstudiante(calificacion.getEstudiante());
        calificacionActual.setIdCalificacion(calificacion.getIdCalificacion());
        calificacionActual.setNota(calificacion.getNota());

        calificacionRepository.save(calificacionActual);
    }

    public boolean existe(Integer id){
        if (buscarPorId(id) != null){
            return true;
        }

        return false;
    }
}