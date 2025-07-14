package com.educacion.dao;

import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para EstudianteDAO
 * Lección 4 - TDD: Pruebas Unitarias y Mocking
 *
 * Estas pruebas usan una base de datos SQLite en memoria
 * para verificar las operaciones CRUD reales
 */
@DisplayName("Pruebas de Integración DAO Estudiante")
class EstudianteDAOTest {

    private EstudianteDAO estudianteDAO;
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite::memory:";

    @BeforeEach
    void setUp() throws SQLException {
        // Crear conexión a base de datos en memoria
        connection = DriverManager.getConnection(DB_URL);

        // Crear tabla para las pruebas
        String createTableSQL = """
            CREATE TABLE estudiantes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100) NOT NULL,
                email VARCHAR(150) UNIQUE NOT NULL,
                edad INTEGER NOT NULL CHECK (edad >= 18 AND edad <= 100),
                curso VARCHAR(200) NOT NULL DEFAULT 'Sin asignar',
                fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
                activo BOOLEAN DEFAULT TRUE
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }

        // Inicializar DAO
        estudianteDAO = new EstudianteDAO(DB_URL);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // ========== PRUEBAS PARA CREAR ESTUDIANTE ==========

    @Test
    @DisplayName("Debería crear estudiante y asignar ID automáticamente")
    void deberiaCrearEstudianteYAsignarIdAutomaticamente() throws SQLException {
        // ARRANGE
        Estudiante estudiante = new Estudiante("Ana Martínez", "ana.martinez@email.com", 24, "Spring Boot");

        // ACT
        Estudiante resultado = estudianteDAO.crear(estudiante);

        // ASSERT
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertTrue(resultado.getId() > 0);
        assertEquals("Ana Martínez", resultado.getNombre());
        assertEquals("ana.martinez@email.com", resultado.getEmail());
        assertEquals(24, resultado.getEdad());
        assertEquals("Spring Boot", resultado.getCurso());
    }

    @Test
    @DisplayName("Debería lanzar excepción si email duplicado")
    void deberiaLanzarExcepcionSiEmailDuplicado() throws SQLException {
        // ARRANGE
        Estudiante estudiante1 = new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java");
        Estudiante estudiante2 = new Estudiante("Pedro Pérez", "juan.perez@email.com", 30, "Python");

        // ACT
        estudianteDAO.crear(estudiante1); // Primer estudiante se crea exitosamente

        // ASSERT
        assertThrows(SQLException.class, () -> estudianteDAO.crear(estudiante2));
    }

    // ========== PRUEBAS PARA OBTENER ESTUDIANTE ==========

    @Test
    @DisplayName("Debería obtener estudiante por ID exitosamente")
    void deberiaObtenerEstudiantePorIdExitosamente() throws SQLException {
        // ARRANGE
        Estudiante estudianteCreado = new Estudiante("María González", "maria.gonzalez@email.com", 22, "Testing");
        Estudiante estudianteGuardado = estudianteDAO.crear(estudianteCreado);

        // ACT
        Optional<Estudiante> resultado = estudianteDAO.obtenerPorId(estudianteGuardado.getId());

        // ASSERT
        assertTrue(resultado.isPresent());
        assertEquals(estudianteGuardado.getId(), resultado.get().getId());
        assertEquals("María González", resultado.get().getNombre());
        assertEquals("maria.gonzalez@email.com", resultado.get().getEmail());
        assertEquals(22, resultado.get().getEdad());
        assertEquals("Testing", resultado.get().getCurso());
    }

    @Test
    @DisplayName("Debería retornar Optional vacío si ID no existe")
    void deberiaRetornarOptionalVacioSiIdNoExiste() throws SQLException {
        // ACT
        Optional<Estudiante> resultado = estudianteDAO.obtenerPorId(999L);

        // ASSERT
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("No debería obtener estudiante eliminado (soft delete)")
    void noDeberiaObtenerEstudianteEliminado() throws SQLException {
        // ARRANGE
        Estudiante estudiante = new Estudiante("Carlos López", "carlos.lopez@email.com", 28, "Java");
        Estudiante estudianteGuardado = estudianteDAO.crear(estudiante);

        // Eliminar estudiante (soft delete)
        estudianteDAO.eliminar(estudianteGuardado.getId());

        // ACT
        Optional<Estudiante> resultado = estudianteDAO.obtenerPorId(estudianteGuardado.getId());

        // ASSERT
        assertFalse(resultado.isPresent());
    }

    // ========== PRUEBAS PARA LISTAR ESTUDIANTES ==========

    @Test
    @DisplayName("Debería obtener todos los estudiantes activos")
    void deberiaObtenerTodosLosEstudiantesActivos() throws SQLException {
        // ARRANGE
        estudianteDAO.crear(new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java"));
        estudianteDAO.crear(new Estudiante("María García", "maria.garcia@email.com", 22, "Spring"));
        estudianteDAO.crear(new Estudiante("Carlos López", "carlos.lopez@email.com", 30, "Testing"));

        // ACT
        List<Estudiante> resultado = estudianteDAO.obtenerTodos();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(3, resultado.size());

        // Verificar que están ordenados por nombre
        assertEquals("Carlos López", resultado.get(0).getNombre());
        assertEquals("Juan Pérez", resultado.get(1).getNombre());
        assertEquals("María García", resultado.get(2).getNombre());
    }

    @Test
    @DisplayName("No debería incluir estudiantes eliminados en la lista")
    void noDeberiaIncluirEstudiantesEliminadosEnLista() throws SQLException {
        // ARRANGE
        Estudiante estudiante1 = estudianteDAO.crear(new Estudiante("Ana Martínez", "ana.martinez@email.com", 24, "Java"));
        Estudiante estudiante2 = estudianteDAO.crear(new Estudiante("Pedro Ruiz", "pedro.ruiz@email.com", 26, "Spring"));

        // Eliminar un estudiante
        estudianteDAO.eliminar(estudiante1.getId());

        // ACT
        List<Estudiante> resultado = estudianteDAO.obtenerTodos();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Pedro Ruiz", resultado.get(0).getNombre());
    }

    // ========== PRUEBAS PARA VERIFICAR EMAIL ==========

    @Test
    @DisplayName("Debería retornar true si email existe")
    void deberiaRetornarTrueSiEmailExiste() throws SQLException {
        // ARRANGE
        String email = "test@email.com";
        estudianteDAO.crear(new Estudiante("Test Usuario", email, 25, "Java"));

        // ACT
        boolean resultado = estudianteDAO.existeEmail(email);

        // ASSERT
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debería retornar false si email no existe")
    void deberiaRetornarFalseSiEmailNoExiste() throws SQLException {
        // ACT
        boolean resultado = estudianteDAO.existeEmail("noexiste@email.com");

        // ASSERT
        assertFalse(resultado);
    }

    @Test
    @DisplayName("No debería encontrar email de estudiante eliminado")
    void noDeberiaEncontrarEmailDeEstudianteEliminado() throws SQLException {
        // ARRANGE
        String email = "eliminado@email.com";
        Estudiante estudiante = estudianteDAO.crear(new Estudiante("Usuario Eliminado", email, 25, "Java"));

        // Eliminar estudiante
        estudianteDAO.eliminar(estudiante.getId());

        // ACT
        boolean resultado = estudianteDAO.existeEmail(email);

        // ASSERT
        assertFalse(resultado);
    }

    // ========== PRUEBAS PARA ACTUALIZAR ESTUDIANTE ==========

    @Test
    @DisplayName("Debería actualizar estudiante exitosamente")
    void deberiaActualizarEstudianteExitosamente() throws SQLException {
        // ARRANGE
        Estudiante estudianteOriginal = estudianteDAO.crear(
                new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java")
        );

        // Modificar datos
        estudianteOriginal.setNombre("Juan Carlos Pérez");
        estudianteOriginal.setEmail("juan.carlos@email.com");
        estudianteOriginal.setEdad(26);
        estudianteOriginal.setCurso("Java Avanzado");

        // ACT
        boolean resultado = estudianteDAO.actualizar(estudianteOriginal);

        // ASSERT
        assertTrue(resultado);

        // Verificar que los cambios se guardaron
        Optional<Estudiante> estudianteActualizado = estudianteDAO.obtenerPorId(estudianteOriginal.getId());
        assertTrue(estudianteActualizado.isPresent());
        assertEquals("Juan Carlos Pérez", estudianteActualizado.get().getNombre());
        assertEquals("juan.carlos@email.com", estudianteActualizado.get().getEmail());
        assertEquals(26, estudianteActualizado.get().getEdad());
        assertEquals("Java Avanzado", estudianteActualizado.get().getCurso());
    }

    @Test
    @DisplayName("Debería retornar false si intenta actualizar estudiante inexistente")
    void deberiaRetornarFalseSiIntentaActualizarEstudianteInexistente() throws SQLException {
        // ARRANGE
        Estudiante estudianteInexistente = new Estudiante(999L, "No Existe", "no.existe@email.com", 25, "Java");

        // ACT
        boolean resultado = estudianteDAO.actualizar(estudianteInexistente);

        // ASSERT
        assertFalse(resultado);
    }

    @Test
    @DisplayName("No debería actualizar estudiante eliminado")
    void noDeberiaActualizarEstudianteEliminado() throws SQLException {
        // ARRANGE
        Estudiante estudiante = estudianteDAO.crear(
                new Estudiante("Pedro López", "pedro.lopez@email.com", 30, "Spring")
        );

        // Eliminar estudiante
        estudianteDAO.eliminar(estudiante.getId());

        // Intentar actualizar
        estudiante.setNombre("Pedro Actualizado");

        // ACT
        boolean resultado = estudianteDAO.actualizar(estudiante);

        // ASSERT
        assertFalse(resultado);
    }
}