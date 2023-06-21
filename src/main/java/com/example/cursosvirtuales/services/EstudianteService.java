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

        Estudiante EstudianteActual = estudianteRepository.findById(EstudianteActualizar.getIdEstudiante()).get();

        EstudianteActual.setIdEstudiante(EstudianteActualizar.getIdEstudiante());
        EstudianteActual.setNombre(EstudianteActualizar.getNombre());
        EstudianteActual.setApellido(EstudianteActualizar.getApellido());
        EstudianteActual.setEmail(EstudianteActualizar.getEmail());
        EstudianteActual.setContrasena(EstudianteActualizar.getContrasena());

        Estudiante peliculaActualizado = estudianteRepository.save(EstudianteActual);
        return peliculaActualizado;
    }

    public void eliminarEstudiante(Integer id) {
        estudianteRepository.deleteById(id);
    }

}