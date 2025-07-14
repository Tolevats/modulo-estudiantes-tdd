package com.educacion.servicio;

import com.educacion.modelo.Estudiante;
import com.educacion.dao.EstudianteDAO;

import java.util.List;
import java.util.Optional;

// Servicio para la lógica de negocio de los Estudiantes.
// Refactorizado

public class EstudianteServicio {

    private final EstudianteDAO estudianteDAO;

    /**
     * Constructor que recibe el DAO
     * Permite inyección de dependencias para testing
     */
    public EstudianteServicio(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public Estudiante crearEstudiante(String nombre, String email, int edad, String curso) {
        validarDatosEstudiante(nombre, email, edad, curso);
        if (estudianteDAO.existeEmail(email)) {
            throw new IllegalArgumentException("Ya existe un estudiante con el email: " + email);
        }
        Estudiante nuevoEstudiante = new Estudiante(nombre, email, edad, curso);
        return estudianteDAO.crear(nuevoEstudiante);
    }

    public Optional<Estudiante> obtenerEstudiantePorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID debe ser un número positivo");
        }
        return estudianteDAO.obtenerPorId(id);
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteDAO.obtenerTodos();
    }

    public Estudiante actualizarEstudiante(Long id, String nombre, String email, int edad, String curso) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID debe ser un número positivo");
        }
        validarDatosEstudiante(nombre, email, edad, curso);

        Estudiante estudiante = estudianteDAO.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe estudiante con ID: " + id));

        if (!estudiante.getEmail().equals(email) && estudianteDAO.existeEmail(email)) {
            throw new IllegalArgumentException("Ya existe un estudiante con el email: " + email);
        }

        estudiante.setNombre(nombre);
        estudiante.setEmail(email);
        estudiante.setEdad(edad);
        estudiante.setCurso(curso);

        boolean actualizado = estudianteDAO.actualizar(estudiante);
        if (!actualizado) {
            throw new RuntimeException("No se pudo actualizar el estudiante con ID: " + id);
        }
        return estudiante;
    }

    public boolean eliminarEstudiante(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID debe ser un número positivo");
        }
        return estudianteDAO.eliminar(id);
    }

    private void validarDatosEstudiante(String nombre, String email, int edad, String curso) {
        if (nombre == null || nombre.trim().isEmpty() || nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre es obligatorio y debe tener al menos 2 caracteres");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
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