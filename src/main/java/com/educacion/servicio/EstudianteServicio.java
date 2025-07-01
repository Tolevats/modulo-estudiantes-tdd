package com.educacion.servicio;

import com.educacion.modelo.Estudiante;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Servicio para la lógica de negocio de los Estudiantes.

public class EstudianteServicio {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    /**
     * Añadir un nuevo estudiante a lista en memoria.
     * @param estudiante El estudiante a crear.
     */
    public void crearEstudiante(Estudiante estudiante) {
        // Añadir el estudiante recibido a la lista.
        this.estudiantes.add(estudiante);
    }

    /**
     * Devolver la lista completa de estudiantes.
     * @return Una lista con todos los estudiantes guardados.
     */
    public List<Estudiante> getTodosLosEstudiantes() {
        // Devolver la lista que la prueba necesita para su verificación.
        return this.estudiantes;
    }

    /**
     * Eliminar un estudiante de la lista basado en su ID.
     * @param id El ID del estudiante a eliminar.
     */
    public void eliminarEstudiante(Long id) {
        // Si la condición es verdadera, el estudiante se elimina.
        this.estudiantes.removeIf(estudiante -> estudiante.getId().equals(id));
    }

    // --- Nuevos métodos para fase GREEN de UPDATE---
    /**
     * Buscar un estudiante por su ID.
     * @param id El ID del estudiante a buscar.
     * @return Un Optional que contiene al estudiante si se encuentra, o un Optional vacío si no.
     */
    public Optional<Estudiante> getEstudiantePorId(Long id) {
        // Se usa la API de Streams de Java para una búsqueda más funcional y moderna.
        // .filter() encuentra los elementos que cumplen la condición.
        // .findFirst() devuelve el primero que encuentra, envuelto en un Optional.
        return this.estudiantes.stream()
                .filter(estudiante -> estudiante.getId().equals(id))
                .findFirst();
    }

    /**
     * Actualizar los datos de un estudiante existente.
     * @param estudianteActualizado El objeto estudiante con los nuevos datos.
     */
    public void actualizarEstudiante(Estudiante estudianteActualizado) {
        // Se reutiliza el método getEstudiantePorId para encontrar al estudiante que se quiere cambiar.
        Optional<Estudiante> estudianteOpt = getEstudiantePorId(estudianteActualizado.getId());

        // ifPresent es un método de Optional que ejecuta el código solo si el Optional contiene un valor.
        // Esto evita tener que comprobar si es nulo.
        estudianteOpt.ifPresent(estudianteEncontrado -> {
            // Se actualizan los campos del estudiante que ya está en la lista.
            estudianteEncontrado.setNombre(estudianteActualizado.getNombre());
            estudianteEncontrado.setCurso(estudianteActualizado.getCurso());
        });
    }
}
