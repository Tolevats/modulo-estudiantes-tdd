package com.educacion.servicio;

import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para EstudianteServicio
 * Siguiendo metodología TDD: RED-GREEN-REFACTOR
 */
class EstudianteServicioTest {

    private EstudianteServicio estudianteServicio;

    @BeforeEach
    void setUp() {
        estudianteServicio = new EstudianteServicio();
    }

    @Test
    @DisplayName("Debería poder instanciar la clase EstudianteServicio")
    void deberiaInstanciarEstudianteServicio() {
        assertNotNull(estudianteServicio);
    }
    // --- Prueba CREATE ---
    @Test
    @DisplayName("Debería poder crear un nuevo estudiante")
    void deberiaCrearUnNuevoEstudiante() {
        // --- Arrange ---
        Estudiante nuevoEstudiante = new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101");
        // --- Act ---
        estudianteServicio.crearEstudiante(nuevoEstudiante);
        // --- Assert ---
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size());
        assertTrue(estudianteServicio.getTodosLosEstudiantes().contains(nuevoEstudiante));
    }
    // --- Prueba DELETE ---
    @Test
    @DisplayName("Debería eliminar un estudiante existente por su ID")
    void deberiaEliminarUnEstudianteExistente() {
        // --- Arrange ---
        Estudiante estudianteExistente = new Estudiante(1L, "Carlos Muro", "cmuro@mail.com", 28, "Historia");
        estudianteServicio.crearEstudiante(estudianteExistente);
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size());
        // --- Act ---
        estudianteServicio.eliminarEstudiante(1L);
        // --- Assert ---
        assertEquals(0, estudianteServicio.getTodosLosEstudiantes().size());
    }
    // --- Prueba UPDATE ---
    @Test
    @DisplayName("Debería actualizar los datos de un estudiante existente")
    void deberiaActualizarUnEstudianteExistente() {
        // --- Arrange ---
        Estudiante estudianteOriginal = new Estudiante(1L, "Ninfa Manríquez", "nmanriquez@mail.com", 24, "Testing Automation I");
        estudianteServicio.crearEstudiante(estudianteOriginal);
        Estudiante estudianteActualizado = new Estudiante(1L, "Ninfa Manríquez", "nmanriquez@mail.com", 24, "Testing Automation II");
        // --- Act ---
        estudianteServicio.actualizarEstudiante(estudianteActualizado);
        // --- Assert ---
        Optional<Estudiante> estudianteRecuperadoOpt = estudianteServicio.getEstudiantePorId(1L);
        assertTrue(estudianteRecuperadoOpt.isPresent(), "El estudiante debería encontrarse en el servicio.");
        assertEquals("Testing Automation II", estudianteRecuperadoOpt.get().getCurso(), "El curso del estudiante debería haberse actualizado.");
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size(), "El número de estudiantes no debería cambiar.");
    }
}
