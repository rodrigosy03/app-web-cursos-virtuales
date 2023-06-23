package com.example.cursosvirtuales.services;

import com.example.cursosvirtuales.entities.Curso;
import com.example.cursosvirtuales.repositories.CursoRepository;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso crear(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> buscarTodo() {
        return (List<Curso>) cursoRepository.findAll();
    }

    public Curso buscarPorId(Integer id) {
        return cursoRepository.findById(id).get();
    }

    public Curso actualizar(Curso cursoActualizar) {
        Curso cursoActual = cursoRepository.findById(cursoActualizar.getIdCurso()).get();
        cursoActual.setIdCurso(cursoActualizar.getIdCurso());
        cursoActual.setNombre(cursoActualizar.getNombre());
        cursoActual.setFechaInicio(cursoActualizar.getFechaInicio());
        cursoActual.setFechaFinalizacion(cursoActualizar.getFechaFinalizacion());
        cursoActual.setProfesor(cursoActualizar.getProfesor());

        Curso cursoActualizado = cursoRepository.save(cursoActual);
        return cursoActualizado;
    }

    public void eliminarCurso(Integer id) {
        cursoRepository.deleteById(id);
    }

    public boolean existe(Integer id){
        if (buscarPorId(id) != null){
            return true;
        }

        return false;
    }
}
