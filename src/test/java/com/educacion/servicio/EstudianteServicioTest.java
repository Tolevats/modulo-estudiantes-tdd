package com.educacion.servicio;

import com.educacion.modelo.Estudiante;
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
        EstudianteServicio estudianteServicio = new EstudianteServicio();
        assertNotNull(estudianteServicio, "La instancia de EstudianteServicio no debería ser nula.");
    }

    // --- Nueva prueba ciclo TDD ---
    @Test
    @DisplayName("Debería poder crear un nuevo estudiante")
    void deberiaCrearUnNuevoEstudiante() {
        // --- 1. FASE DE PREPARACIÓN (Arrange) ---
        // Crear instancia del servicio a probar
        EstudianteServicio estudianteServicio = new EstudianteServicio();
        // Crear el objeto estudiante que se quiere guardar
        Estudiante nuevoEstudiante = new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101");

        // --- 2. FASE DE ACTUACIÓN (Act) ---
        // Llamar al método que se quiere probar
        // IntelliJ lo marcará en rojo porque aún no existe. ¡Esta es la fase RED!
        estudianteServicio.crearEstudiante(nuevoEstudiante);

        // --- 3. FASE DE AFIRMACIÓN (Assert) ---
        // Verificar que el estudiante fue realmente añadido.
        // Se supone que el sitio
        // Este método tampoco existe, lo que contribuye también al fallo
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size(), "Debería haber un estudiante en el servicio.");
        assertTrue(estudianteServicio.getTodosLosEstudiantes().contains(nuevoEstudiante), "La lista debería contener al estudiante recién creado.");
    }
}
