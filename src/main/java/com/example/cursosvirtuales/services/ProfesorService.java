package com.example.cursosvirtuales.services;

import com.example.cursosvirtuales.entities.Profesor;
import com.example.cursosvirtuales.repositories.ProfesorRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;


    public Profesor crear(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public List<Profesor> buscarTodo(){
        return(List<Profesor>) profesorRepository.findAll();
    }

    public Profesor buscarPorId(Integer id) {
        return profesorRepository.findById(id).get();
    }

    public Profesor actualizar(Profesor actualizarProfesor) {
        Profesor actualizarProfe = profesorRepository.findById(actualizarProfesor.getIdProfesor()).get();
        actualizarProfe.setIdProfesor(actualizarProfesor.getIdProfesor());
        actualizarProfe.setNombre(actualizarProfesor.getNombre());
        actualizarProfe.setApellido(actualizarProfesor.getApellido());
        actualizarProfe.setContrasena(actualizarProfesor.getContrasena());
        actualizarProfe.setEmail(actualizarProfesor.getEmail());

        Profesor nuevoProfesor = profesorRepository.save(actualizarProfe);
        return nuevoProfesor;
    }

    public void eliminarProfesor(Integer id) {
        profesorRepository.deleteById(id);
    }

    public boolean existe(Integer id){
        if (buscarPorId(id) != null){
            return true;
        }

        return false;
    }

}
