package com.educacion.servicio;

import com.educacion.modelo.Estudiante;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación mínima para pasar la prueba de creación.
 */

public class EstudianteServicio {
    // Usar una lista en memoria para simular el almacenamiento de datos por ahora.
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
}
