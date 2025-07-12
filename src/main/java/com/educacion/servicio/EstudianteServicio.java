package com.educacion.servicio;

import com.educacion.modelo.Estudiante;
import com.educacion.dao.EstudianteDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Servicio para la lógica de negocio de los Estudiantes.
// Refactorizado para usar base de datos real

public class EstudianteServicio {

    private final EstudianteDAO estudianteDAO;

    /**
     * Constructor que recibe el DAO
     * Permite inyección de dependencias para testing
     */
    public EstudianteServicio(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    // --- Crea un nuevo estudiante ---
    public Estudiante crearEstudiante(String nombre, String email, int edad, String curso) {
        try {
            // Validaciones de negocio
            validarDatosEstudiante(nombre, email, edad, curso);

            // Verificar email duplicado
            if (estudianteDAO.existeEmail(email)) {
                throw new IllegalArgumentException("Ya existe un estudiante con el email: " + email);
            }

            // Crear estudiante
            Estudiante nuevoEstudiante = new Estudiante(nombre, email, edad, curso);

            // Persistir en base de datos
            return estudianteDAO.crear(nuevoEstudiante);

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear estudiante: " + e.getMessage(), e);
        }
    }

    // --- Obtener un estudiante por ID ---
    public Optional<Estudiante> obtenerEstudiantePorId(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID debe ser un número positivo");
            }

            return estudianteDAO.obtenerPorId(id);

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener estudiante: " + e.getMessage(), e);
        }
    }

    // --- Obtener todos los estudiantes activos ---
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        try {
            return estudianteDAO.obtenerTodos();

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener estudiantes: " + e.getMessage(), e);
        }
    }

    // --- Actualizar un estudiante existente ---
    public Estudiante actualizarEstudiante(Long id, String nombre, String email, int edad, String curso) {
        try {
            // Validaciones
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID debe ser un número positivo");
            }

            validarDatosEstudiante(nombre, email, edad, curso);

            // Verificar que el estudiante existe
            Optional<Estudiante> estudianteExistente = estudianteDAO.obtenerPorId(id);
            if (estudianteExistente.isEmpty()) {
                throw new IllegalArgumentException("No existe estudiante con ID: " + id);
            }

            // Verificar email duplicado (solo si cambió)
            Estudiante estudiante = estudianteExistente.get();
            if (!estudiante.getEmail().equals(email) && estudianteDAO.existeEmail(email)) {
                throw new IllegalArgumentException("Ya existe un estudiante con el email: " + email);
            }

            // Actualizar datos
            estudiante.setNombre(nombre);
            estudiante.setEmail(email);
            estudiante.setEdad(edad);
            estudiante.setCurso(curso);

            // Persistir cambios
            boolean actualizado = estudianteDAO.actualizar(estudiante);
            if (!actualizado) {
                throw new RuntimeException("No se pudo actualizar el estudiante");
            }

            return estudiante;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estudiante: " + e.getMessage(), e);
        }
    }

    // --- Eliminar un estudiante por ID ---
    public boolean eliminarEstudiante(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID debe ser un número positivo");
            }

            return estudianteDAO.eliminar(id);

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estudiante: " + e.getMessage(), e);
        }
    }

    // --- Validaciones de negocio para datos del estudiante ---
    private void validarDatosEstudiante(String nombre, String email, int edad, String curso) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }

        if (edad < 18 || edad > 100) {
            throw new IllegalArgumentException("La edad debe estar entre 18 y 100 años");
        }

        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("El curso es obligatorio");
        }
    }
}