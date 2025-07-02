package com.educacion.servicio;

import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    // --- Prueba CREATE ---
    @Test
    @DisplayName("[CREATE] Debería poder crear un nuevo estudiante")
    void deberiaCrearUnNuevoEstudiante() {
        // Arrange
        Estudiante nuevoEstudiante = new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101");
        // Act
        estudianteServicio.crearEstudiante(nuevoEstudiante);
        // Assert
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size());
        assertTrue(estudianteServicio.getTodosLosEstudiantes().contains(nuevoEstudiante));
    }

    // --- Pruebas READ ---
    @Test
    @DisplayName("[READ 1] Debería devolver todos los estudiantes añadidos")
    void deberiaDevolverTodosLosEstudiantes() {
        // Arrange
        estudianteServicio.crearEstudiante(new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101"));
        estudianteServicio.crearEstudiante(new Estudiante(2L, "Carlos Muro", "cmuro@mail.com", 28, "Java"));
        // Act
        List<Estudiante> estudiantes = estudianteServicio.getTodosLosEstudiantes();
        // Assert
        assertNotNull(estudiantes);
        assertEquals(2, estudiantes.size(), "Debería haber 2 estudiantes en la lista.");
    }
    @Test
    @DisplayName("[READ 2] Debería encontrar un estudiante por su ID")
    void deberiaEncontrarUnEstudiantePorId() {
        // Arrange
        estudianteServicio.crearEstudiante(new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101"));
        // Act
        Optional<Estudiante> estudianteOpt = estudianteServicio.getEstudiantePorId(1L);
        // Assert
        assertTrue(estudianteOpt.isPresent(), "El estudiante debería haber sido encontrado.");
        assertEquals("Juana Quintana", estudianteOpt.get().getNombre());
    }
    @Test
    @DisplayName("[READ 3] Debería devolver Optional vacío para un ID inexistente")
    void deberiaDevolverOptionalVacioParaIdInexistente() {
        // Act
        Optional<Estudiante> estudianteOpt = estudianteServicio.getEstudiantePorId(99L);
        // Assert
        assertFalse(estudianteOpt.isPresent(), "No debería encontrarse un estudiante con un ID que no existe.");
    }

    // --- Prueba UPDATE ---
    @Test
    @DisplayName("[UPDATE] Debería actualizar los datos de un estudiante existente")
    void deberiaActualizarUnEstudianteExistente() {
        // Arrange
        Estudiante estudianteOriginal = new Estudiante(3L, "Ninfa Manríquez", "nmanriquez@mail.com", 24, "Testing Automation I");
        estudianteServicio.crearEstudiante(estudianteOriginal);
        Estudiante estudianteActualizado = new Estudiante(3L, "Ninfa Manríquez", "nmanriquez@mail.com", 24, "Testing Automation II");
        // Act
        estudianteServicio.actualizarEstudiante(estudianteActualizado);
        // Assert
        Optional<Estudiante> estudianteRecuperadoOpt = estudianteServicio.getEstudiantePorId(3L);
        assertTrue(estudianteRecuperadoOpt.isPresent(), "El estudiante debería encontrarse en el servicio.");
        assertEquals("Testing Automation II", estudianteRecuperadoOpt.get().getCurso(), "El curso del estudiante debería haberse actualizado.");
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size(), "El número de estudiantes no debería cambiar.");
    }

    // --- Prueba DELETE ---
    @Test
    @DisplayName("[DELETE] Debería eliminar un estudiante existente por su ID")
    void deberiaEliminarUnEstudianteExistente() {
        // Arrange
        Estudiante estudianteExistente = new Estudiante(1L, "Juana Quintana", "jquintana@mail.com", 33, "Testing 101");
        estudianteServicio.crearEstudiante(estudianteExistente);
        assertEquals(1, estudianteServicio.getTodosLosEstudiantes().size());
        // Act
        estudianteServicio.eliminarEstudiante(1L);
        // Assert
        assertEquals(0, estudianteServicio.getTodosLosEstudiantes().size());
        assertFalse(estudianteServicio.getEstudiantePorId(1L).isPresent());
    }

}
