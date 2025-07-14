package com.educacion.servicio;

import com.educacion.dao.EstudianteDAO;
import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para EstudianteServicio usando Mockito
 * Lección 4 - TDD: Pruebas Unitarias y Mocking
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del Servicio Estudiante con Mocking")
class EstudianteServicioTest {

    @Mock
    private EstudianteDAO estudianteDAO;

    private EstudianteServicio estudianteServicio;

    @BeforeEach
    void setUp() {
        estudianteServicio = new EstudianteServicio(estudianteDAO);
    }

    // ========== Pruebas CREATE para obtener Estudiante ==========
    @Test
    @DisplayName("Debería obtener estudiante por ID exitosamente")
    void deberiaObtenerEstudiantePorIdExitosamente() throws SQLException {
        // ARRANGE
        Long id = 1L;
        Estudiante estudianteEsperado = new Estudiante(id, "Carlos Mendoza", "carlos.mendoza@email.com", 28, "Testing");

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.of(estudianteEsperado));

        // ACT
        Optional<Estudiante> resultado = estudianteServicio.obtenerEstudiantePorId(id);

        // ASSERT
        assertTrue(resultado.isPresent());
        assertEquals(estudianteEsperado, resultado.get());
        verify(estudianteDAO).obtenerPorId(id);
    }

    @Test
    @DisplayName("Debería retornar Optional vacío si estudiante no existe")
    void deberiaRetornarOptionalVacioSiEstudianteNoExiste() throws SQLException {
        // ARRANGE
        Long id = 99L;

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.empty());

        // ACT
        Optional<Estudiante> resultado = estudianteServicio.obtenerEstudiantePorId(id);

        // ASSERT
        assertFalse(resultado.isPresent());
        verify(estudianteDAO).obtenerPorId(id);
    }

    @Test
    @DisplayName("Debería lanzar excepción si ID es nulo o inválido")
    void deberiaLanzarExcepcionSiIdEsNuloOInvalido() {
        // ACT & ASSERT
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> estudianteServicio.obtenerEstudiantePorId(null)
        );

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> estudianteServicio.obtenerEstudiantePorId(0L)
        );

        assertEquals("ID debe ser un número positivo", exception1.getMessage());
        assertEquals("ID debe ser un número positivo", exception2.getMessage());
        verifyNoInteractions(estudianteDAO);
    }

    // ========== Pruebas READ para listar Estudiantes ==========
    @Test
    @DisplayName("Debería obtener todos los estudiantes exitosamente")
    void deberiaObtenerTodosLosEstudiantesExitosamente() throws SQLException {
        // ARRANGE
        List<Estudiante> estudiantesEsperados = Arrays.asList(
                new Estudiante(1L, "Juan Pérez", "juan.perez@email.com", 25, "Java"),
                new Estudiante(2L, "María García", "maria.garcia@email.com", 22, "Spring"),
                new Estudiante(3L, "Carlos López", "carlos.lopez@email.com", 30, "Testing")
        );

        when(estudianteDAO.obtenerTodos()).thenReturn(estudiantesEsperados);

        // ACT
        List<Estudiante> resultado = estudianteServicio.obtenerTodosLosEstudiantes();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals(estudiantesEsperados, resultado);
        verify(estudianteDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Debería retornar lista vacía si no hay estudiantes")
    void deberiaRetornarListaVaciaSiNoHayEstudiantes() throws SQLException {
        // ARRANGE
        when(estudianteDAO.obtenerTodos()).thenReturn(Arrays.asList());

        // ACT
        List<Estudiante> resultado = estudianteServicio.obtenerTodosLosEstudiantes();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(estudianteDAO).obtenerTodos();
    }

    // ========== Pruebas UPDATE para actualizar Estudiante ==========
    @Test
    @DisplayName("Debería actualizar estudiante exitosamente")
    void deberiaActualizarEstudianteExitosamente() throws SQLException {
        // ARRANGE
        Long id = 1L;
        String nombre = "Juan Carlos Pérez";
        String email = "juan.carlos@email.com";
        int edad = 26;
        String curso = "Java Avanzado";

        Estudiante estudianteExistente = new Estudiante(id, "Juan Pérez", "juan.perez@email.com", 25, "Java");

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.of(estudianteExistente));
        when(estudianteDAO.existeEmail(email)).thenReturn(false);
        when(estudianteDAO.actualizar(any(Estudiante.class))).thenReturn(true);

        // ACT
        Estudiante resultado = estudianteServicio.actualizarEstudiante(id, nombre, email, edad, curso);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(nombre, resultado.getNombre());
        assertEquals(email, resultado.getEmail());
        assertEquals(edad, resultado.getEdad());
        assertEquals(curso, resultado.getCurso());

        verify(estudianteDAO).obtenerPorId(id);
        verify(estudianteDAO).existeEmail(email);
        verify(estudianteDAO).actualizar(any(Estudiante.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción si estudiante a actualizar no existe")
    void deberiaLanzarExcepcionSiEstudianteAActualizarNoExiste() throws SQLException {
        // ARRANGE
        Long id = 99L;

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.empty());

        // ACT & ASSERT
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> estudianteServicio.actualizarEstudiante(id, "Nombre", "email@test.com", 25, "Curso")
        );

        assertEquals("No existe estudiante con ID: " + id, exception.getMessage());
        verify(estudianteDAO).obtenerPorId(id);
        verify(estudianteDAO, never()).actualizar(any(Estudiante.class));
    }

    // ========== Pruebas DELETE para eliminar Estudiante ==========
    @Test
    @DisplayName("Debería eliminar estudiante existosamente")
    void deberiaEliminarEstudianteExitosamente() throws SQLException {
        // ARRANGE
        Long id = 1L;

        when(estudianteDAO.eliminar(id)).thenReturn(true);

        // ACT
        boolean resultado = estudianteServicio.eliminarEstudiante(id);

        // ASSERT
        assertTrue(resultado);
        verify(estudianteDAO).eliminar(id);
    }

    @Test
    @DisplayName("Debería retornar false si no se puede eliminar estudiante")
    void deberiaRetornarFalseSiNoPuedeEliminarEstudiante() throws SQLException {
        // ARRANGE
        Long id = 99L;

        when(estudianteDAO.eliminar(id)).thenReturn(false);

        // ACT
        boolean resultado = estudianteServicio.eliminarEstudiante(id);

        // ASSERT
        assertFalse(resultado);
        verify(estudianteDAO).eliminar(id);
    }

    @Test
    @DisplayName("Debería lanzar excepción si ID para eliminar es inválido")
    void deberiaLanzarExcepcionSiIdParaEliminarEsInvalido() {
        // ACT & ASSERT
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> estudianteServicio.eliminarEstudiante(null)
        );

        assertEquals("ID debe ser un número positivo", exception.getMessage());
        verifyNoInteractions(estudianteDAO);
    }

    // ========== Pruebas para manejo de excepciones SQL ==========
    @Test
    @DisplayName("Debería manejar SQLException al crear estudiante")
    void deberiaManejarSQLExceptionAlCrearEstudiante() throws SQLException {
        // ARRANGE
        String nombre = "Test";
        String email = "test@email.com";
        int edad = 25;
        String curso = "Java";

        when(estudianteDAO.existeEmail(email)).thenReturn(false);
        when(estudianteDAO.crear(any(Estudiante.class))).thenThrow(new SQLException("Error de BD"));

        // ACT & ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> estudianteServicio.crearEstudiante(nombre, email, edad, curso)
        );

        assertTrue(exception.getMessage().contains("Error al crear estudiante"));
        verify(estudianteDAO).existeEmail(email);
        verify(estudianteDAO).crear(any(Estudiante.class));
    }
}
