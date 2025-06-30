package com.educacion.servicio;

import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para EstudianteServicio
 * Siguiendo metodología TDD: RED-GREEN-REFACTOR
 */
class EstudianteServicioTest {
    // 1. Declaramos la instancia del servicio a nivel de clase.
    private EstudianteServicio estudianteServicio;
    // 2. Este método se ejecutará ANTES de cada prueba.
    @BeforeEach
    void setUp() {
        // Crear una instancia nueva y limpia para cada prueba, asegurando el aislamiento
        estudianteServicio = new EstudianteServicio();
    }

    @Test
    @DisplayName("Debería poder instanciar la clase EstudianteServicio")
    void deberiaInstanciarEstudianteServicio() {
        // La aserción sigue siendo la misma, pero ya no se crea el servicio aquí
        assertNotNull(estudianteServicio, "La instancia de EstudianteServicio no debería ser nula.");
    }

    @Test
    @DisplayName("Debería poder crear un nuevo estudiante")
    void deberiaCrearUnNuevoEstudiante() {
        // --- Arrange ---
        Estudiante nuevoEstudiante = new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101");

        // --- Act ---
        estudianteServicio.crearEstudiante(nuevoEstudiante);

        // --- Assert ---
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size(), "Debería haber un estudiante en el servicio.");
        assertTrue(estudianteServicio.getTodosLosEstudiantes().contains(nuevoEstudiante), "La lista debería contener al estudiante recién creado.");
    }

    // --- NUEVA PRUEBA (RED) ---
    @Test
    @DisplayName("Debería eliminar un estudiante existente por su ID")
    void deberiaEliminarUnEstudianteExistente() {
        // --- Arrange ---
        // Primero, se necesita un estudiante en el servicio para poder eliminarlo.
        Estudiante estudianteExistente = new Estudiante(1L, "Carlos Muro", "cmuro@mail.com", 28, "Historia");
        estudianteServicio.crearEstudiante(estudianteExistente);
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size(), "Precondición: Debería haber 1 estudiante antes de eliminar.");

        // --- Act ---
        // Llamar al método que queremos probar (aún no existe).
        estudianteServicio.eliminarEstudiante(1L);

        // --- Assert ---
        // Verificar que la lista de estudiantes ahora está vacía
        assertEquals(0, estudianteServicio.getTodosLosEstudiantes().size(), "El tamaño de la lista debería ser 0 después de eliminar.");
    }
}
