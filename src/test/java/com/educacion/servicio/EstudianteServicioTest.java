package com.educacion.servicio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para EstudianteServicio
 * Siguiendo metodología TDD: RED-GREEN-REFACTOR
 */
class EstudianteServicioTest {

    // La anotación @Test le dice a JUnit que este método es una prueba que debe ejecutar.
    @Test
    // @DisplayName permite dar una descripción legible a prueba.
    @DisplayName("Debería poder instanciar la clase EstudianteServicio")
    void deberiaInstanciarEstudianteServicio() {
        // --- FASE DE PREPARACIÓN (Arrange) ---
        // En este caso, no se necesita preparar nada.

        // --- FASE DE ACTUACIÓN (Act) ---
        // Aquí es donde la prueba fallará, porque la clase 'EstudianteServicio' aún no existe.
        // IntelliJ marcará 'EstudianteServicio' en rojo.
        EstudianteServicio estudianteServicio = new EstudianteServicio();

        // --- FASE DE ASERCIÓN (Assert) ---
        // Verificar que el objeto creado no sea nulo.
        // Esta línea no se llegará a ejecutar al principio, porque el código no compilará.
        assertNotNull(estudianteServicio, "La instancia de EstudianteServicio no debería ser nula.");
    }
}
