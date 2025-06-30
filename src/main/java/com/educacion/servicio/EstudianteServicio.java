package com.educacion.servicio;

import com.educacion.modelo.Estudiante;

import java.util.ArrayList;
import java.util.List;

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
        // removeIf para encontrar y eliminar el estudiante cuyo ID coincida.
        // La expresión "estudiante -> estudiante.getId().equals(id)" es una lambda.
        // Significa: "para cada 'estudiante' en la lista, comprueba si su ID es igual al 'id' proporcionado".
        // Si la condición es verdadera, el elemento se elimina.
        this.estudiantes.removeIf(estudiante -> estudiante.getId().equals(id));
    }
}
