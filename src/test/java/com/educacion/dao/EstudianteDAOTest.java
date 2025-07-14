package com.educacion.dao;

import com.educacion.modelo.Estudiante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Integración DAO Estudiante")
class EstudianteDAOTest {

    private EstudianteDAO estudianteDAO;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite::memory:";

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
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
        estudianteDAO = new EstudianteDAOImpl(() -> connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Debería crear estudiante y asignar ID")
    void deberiaCrearEstudianteYAsignarId() {
        Estudiante estudiante = new Estudiante("Ana Martínez", "ana.martinez@email.com", 24, "Spring Boot");
        Estudiante resultado = estudianteDAO.crear(estudiante);
        assertNotNull(resultado.getId());
        assertTrue(resultado.getId() > 0);
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al duplicar email")
    void deberiaLanzarExcepcionSiEmailDuplicado() {
        estudianteDAO.crear(new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java"));
        Estudiante estudiante2 = new Estudiante("Pedro Pérez", "juan.perez@email.com", 30, "Python");
        // KEY FIX: We now expect a RuntimeException, not a SQLException
        assertThrows(RuntimeException.class, () -> estudianteDAO.crear(estudiante2));
    }

    @Test
    @DisplayName("Debería obtener estudiante por ID")
    void deberiaObtenerEstudiantePorId() {
        Estudiante guardado = estudianteDAO.crear(new Estudiante("María González", "maria.gonzalez@email.com", 22, "Testing"));
        Optional<Estudiante> resultado = estudianteDAO.obtenerPorId(guardado.getId());
        assertTrue(resultado.isPresent());
        assertEquals(guardado.getId(), resultado.get().getId());
    }

    @Test
    @DisplayName("No debería obtener estudiante eliminado (soft delete)")
    void noDeberiaObtenerEstudianteEliminado() {
        Estudiante estudiante = estudianteDAO.crear(new Estudiante("Carlos López", "carlos.lopez@email.com", 28, "Java"));
        estudianteDAO.eliminar(estudiante.getId());
        Optional<Estudiante> resultado = estudianteDAO.obtenerPorId(estudiante.getId());
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debería obtener todos los estudiantes activos")
    void deberiaObtenerTodosLosEstudiantesActivos() {
        estudianteDAO.crear(new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java"));
        estudianteDAO.crear(new Estudiante("María García", "maria.garcia@email.com", 22, "Spring"));
        List<Estudiante> resultado = estudianteDAO.obtenerTodos();
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Debería actualizar estudiante exitosamente")
    void deberiaActualizarEstudianteExitosamente() {
        Estudiante original = estudianteDAO.crear(new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java"));
        original.setCurso("Java Avanzado");
        boolean resultado = estudianteDAO.actualizar(original);
        assertTrue(resultado);

        Optional<Estudiante> actualizado = estudianteDAO.obtenerPorId(original.getId());
        assertTrue(actualizado.isPresent());
        assertEquals("Java Avanzado", actualizado.get().getCurso());
    }
}