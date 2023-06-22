package com.example.cursosvirtuales.services;

import com.example.cursosvirtuales.entities.Estudiante;
import com.example.cursosvirtuales.repositories.EstudianteRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Estudiante crear(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);

    }

    public List<Estudiante> buscarTodo(){
        return(List<Estudiante>) estudianteRepository.findAll();
    }

    public Estudiante buscarPorId(Integer id) {
        return estudianteRepository.findById(id).get();
    }

    public Estudiante actualizar(Estudiante EstudianteActualizar) {

        Estudiante estudianteActual = estudianteRepository.findById(EstudianteActualizar.getIdEstudiante()).get();

        estudianteActual.setIdEstudiante(EstudianteActualizar.getIdEstudiante());
        estudianteActual.setNombre(EstudianteActualizar.getNombre());
        estudianteActual.setApellido(EstudianteActualizar.getApellido());
        estudianteActual.setEmail(EstudianteActualizar.getEmail());
        estudianteActual.setContrasena(EstudianteActualizar.getContrasena());

        Estudiante estudianteActualizado = estudianteRepository.save(estudianteActual);
        return estudianteActualizado;
    }

    public void eliminarEstudiante(Integer id) {
        estudianteRepository.deleteById(id);
    }

}