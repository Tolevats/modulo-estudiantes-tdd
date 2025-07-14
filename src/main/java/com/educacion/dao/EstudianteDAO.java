package com.educacion.dao;

import com.educacion.modelo.Estudiante;
import java.util.List;
import java.util.Optional;

/**
 * Interface for Data Access Object for Estudiante.
 * Defines the contract for persistence operations.
 */
public interface EstudianteDAO {
    Estudiante crear(Estudiante estudiante);
    Optional<Estudiante> obtenerPorId(Long id);
    List<Estudiante> obtenerTodos();
    boolean actualizar(Estudiante estudiante);
    boolean eliminar(Long id);
    boolean existeEmail(String email);
}
