package com.educacion.servicio;

import com.educacion.dao.EstudianteDAO;
import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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
    void deberiaObtenerEstudiantePorIdExitosamente() {
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
    void deberiaRetornarOptionalVacioSiEstudianteNoExiste() {
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
        assertThrows(IllegalArgumentException.class, () -> estudianteServicio.obtenerEstudiantePorId(null));
        assertThrows(IllegalArgumentException.class, () -> estudianteServicio.obtenerEstudiantePorId(0L));
        verifyNoInteractions(estudianteDAO);
    }

    // ========== Pruebas READ para listar Estudiantes ==========
    @Test
    @DisplayName("Debería obtener todos los estudiantes exitosamente")
    void deberiaObtenerTodosLosEstudiantesExitosamente() {
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
    void deberiaRetornarListaVaciaSiNoHayEstudiantes() {
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
    void deberiaActualizarEstudianteExitosamente() {
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
    void deberiaLanzarExcepcionSiEstudianteAActualizarNoExiste() {
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
    void deberiaEliminarEstudianteExitosamente() {
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
    void deberiaRetornarFalseSiNoPuedeEliminarEstudiante() {
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
    @DisplayName("Debería propagar RuntimeException del DAO al crear estudiante")
    void deberiaPropagarRuntimeExceptionDelDAO() {
        // ARRANGE
        String nombre = "Test";
        String email = "test@email.com";
        int edad = 25;
        String curso = "Java";

        when(estudianteDAO.existeEmail(email)).thenReturn(false);
        when(estudianteDAO.crear(any(Estudiante.class))).thenThrow(new RuntimeException("Error de BD"));

        // ACT & ASSERT
        assertThrows(RuntimeException.class, () -> {
                estudianteServicio.crearEstudiante(nombre, email, edad, curso);
        });
    }

    /**
    * Pruebas adicionales para alcanzar 80% de cobertura en EstudianteServicio
    * Casos edge y branches no cubiertas
    */
    @Test
    @DisplayName("Debería fallar al crear estudiante con nombre de 1 carácter")
    public void deberiaFallarConNombreUnCaracter() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("A", "test@test.com", 20, "Java");
        });

        assertEquals("El nombre es obligatorio y debe tener al menos 2 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Debería fallar al crear estudiante con nombre solo espacios")
    public void deberiaFallarConNombreSoloEspacios() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("   ", "test@test.com", 20, "Java");
        });

        assertEquals("El nombre es obligatorio y debe tener al menos 2 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Debería fallar al crear estudiante con edad límite inferior")
    public void deberiaFallarConEdadMenorA18() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("Juan Pérez", "juan@test.com", 17, "Java");
        });

        assertEquals("La edad debe estar entre 18 y 100 años", exception.getMessage());
    }

    @Test
    @DisplayName("Debería fallar al crear estudiante con edad límite superior")
    public void deberiaFallarConEdadMayorA100() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("Juan Pérez", "juan@test.com", 101, "Java");
        });

        assertEquals("La edad debe estar entre 18 y 100 años", exception.getMessage());
    }

    @Test
    @DisplayName("Debería fallar al crear estudiante con curso solo espacios")
    public void deberiaFallarConCursoSoloEspacios() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("Juan Pérez", "juan@test.com", 20, "   ");
        });

        assertEquals("El curso es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debería fallar al actualizar con email existente de otro estudiante")
    public void deberiaFallarAlActualizarConEmailExistente() {
        // Arrange
        Long id = 1L;
        Estudiante estudianteExistente = new Estudiante(id, "Juan Original", "juan@test.com", 20, "Java");

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.of(estudianteExistente));
        when(estudianteDAO.existeEmail("nuevo@test.com")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.actualizarEstudiante(id, "Juan Actualizado", "nuevo@test.com", 25, "Python");
        });

        assertEquals("Ya existe un estudiante con el email: nuevo@test.com", exception.getMessage());
    }

    @Test
    @DisplayName("Debería actualizar correctamente cuando se mantiene el mismo email")
    public void deberiaActualizarConMismoEmail() {
        // Arrange
        Long id = 1L;
        Estudiante estudianteExistente = new Estudiante(id, "Juan Original", "juan@test.com", 20, "Java");

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.of(estudianteExistente));
        when(estudianteDAO.actualizar(any(Estudiante.class))).thenReturn(true);

        // Act
        Estudiante resultado = estudianteServicio.actualizarEstudiante(id, "Juan Actualizado", "juan@test.com", 25, "Python");

        // Assert
        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("juan@test.com", resultado.getEmail());
        assertEquals(25, resultado.getEdad());
        assertEquals("Python", resultado.getCurso());

        verify(estudianteDAO).actualizar(estudianteExistente);
        verify(estudianteDAO, never()).existeEmail(anyString());
    }

    @Test
    @DisplayName("Debería fallar cuando actualizar retorna false")
    public void deberiaFallarCuandoActualizarRetornaFalse() {
        // Arrange
        Long id = 1L;
        Estudiante estudianteExistente = new Estudiante(id, "Juan Original", "juan@test.com", 20, "Java");

        when(estudianteDAO.obtenerPorId(id)).thenReturn(Optional.of(estudianteExistente));
        when(estudianteDAO.actualizar(any(Estudiante.class))).thenReturn(false);
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            estudianteServicio.actualizarEstudiante(id, "Juan Actualizado", "juan@test.com", 25, "Python");
        });
        assertEquals("No se pudo actualizar el estudiante con ID: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Debería validar emails con formatos límite válidos")
    public void deberiaValidarEmailsConFormatosLimite() {
        // Arrange
        when(estudianteDAO.existeEmail(anyString())).thenReturn(false);
        when(estudianteDAO.crear(any(Estudiante.class))).thenReturn(new Estudiante(1L, "Test", "test@domain.co", 20, "Test"));
        // Act & Assert - Email mínimo válido
        assertDoesNotThrow(() -> {
            estudianteServicio.crearEstudiante("Test User", "a@b.co", 20, "Test");
        });
        // Act & Assert - Email con múltiples puntos
        assertDoesNotThrow(() -> {
            estudianteServicio.crearEstudiante("Test User", "test.user@domain.co.uk", 20, "Test");
        });
    }

    @Test
    @DisplayName("Debería fallar con formato de email inválido - casos específicos")
    public void deberiaFallarConEmailInvalido() {
        // Email null
        assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("Test User", null, 20, "Test");
        });

        // Email vacío
        assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("Test User", "", 20, "Test");
        });

        // Email solo espacios
        assertThrows(IllegalArgumentException.class, () -> {
            estudianteServicio.crearEstudiante("Test User", "   ", 20, "Test");
        });
    }

    @Test
    @DisplayName("Debería crear estudiante con edad límite válida (18 y 100)")
    public void deberiaCrearEstudianteConEdadLimite() {
        // Arrange
        when(estudianteDAO.existeEmail(anyString())).thenReturn(false);
        when(estudianteDAO.crear(any(Estudiante.class))).thenReturn(new Estudiante(1L, "Test", "test@test.com", 18, "Test"));
        // Act & Assert - Edad mínima válida (18)
        assertDoesNotThrow(() -> {
            estudianteServicio.crearEstudiante("Test User 18", "test18@test.com", 18, "Test");
        });
        // Act & Assert - Edad máxima válida (100)
        assertDoesNotThrow(() -> {
            estudianteServicio.crearEstudiante("Test User 100", "test100@test.com", 100, "Test");
        });
    }}
