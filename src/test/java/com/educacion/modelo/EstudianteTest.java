package com.educacion.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Modelo Estudiante")
public class EstudianteTest {

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada prueba
        estudiante = new Estudiante(null, "Juan Pérez", "juan.perez@email.com", 25, "Java Básico", LocalDateTime.now(), true);
    }

    @Test
    @DisplayName("Debería crear estudiante con constructor completo")
    public void deberiaCrearEstudianteConConstructorCompleto() {
        // ARRANGE
        Long id = 1L;
        String nombre = "Ana García";
        String email = "ana.garcia@email.com";
        int edad = 22;
        String curso = "Testing Automation";
        LocalDateTime fechaRegistro = LocalDateTime.of(2024, 1, 15, 10, 30);
        boolean activo = false;
        // ACT
        Estudiante nuevoEstudiante = new Estudiante(id, nombre, email, edad, curso, fechaRegistro, activo);
        // ASSERT
        assertNotNull(nuevoEstudiante);
        assertEquals(nombre, nuevoEstudiante.getNombre());
        assertEquals(email, nuevoEstudiante.getEmail());
        assertEquals(edad, nuevoEstudiante.getEdad());
        assertEquals(curso, nuevoEstudiante.getCurso());
        assertEquals(fechaRegistro, nuevoEstudiante.getFechaRegistro());
        assertFalse(nuevoEstudiante.isActivo());
    }

    @Test
    @DisplayName("Debería crear estudiante con constructor con ID")
    void deberiaCrearEstudianteConConstructorConId() {
        // ARRANGE
        Long id = 1L;
        String nombre = "Carlos López";
        String email = "carlos.lopez@email.com";
        int edad = 30;
        String curso = "Spring Boot";
        // ACT
        Estudiante estudianteConId = new Estudiante(id, nombre, email, edad, curso);
        // ASSERT
        assertNotNull(estudianteConId);
        assertEquals(id, estudianteConId.getId());
        assertEquals(nombre, estudianteConId.getNombre());
        assertEquals(email, estudianteConId.getEmail());
        assertEquals(edad, estudianteConId.getEdad());
        assertEquals(curso, estudianteConId.getCurso());
    }

    @Test
    @DisplayName("Debería permitir modificar nombre correctamente")
    void deberiaPermitirModificarNombre() {
        // ARRANGE
        String nuevoNombre = "Juan Carlos Pérez";
        // ACT
        estudiante.setNombre(nuevoNombre);
        // ASSERT
        assertEquals(nuevoNombre, estudiante.getNombre());
    }

    @Test
    @DisplayName("Debería permitir modificar email correctamente")
    void deberiaPermitirModificarEmail() {
        // ARRANGE
        String nuevoEmail = "juan.carlos@email.com";
        // ACT
        estudiante.setEmail(nuevoEmail);
        // ASSERT
        assertEquals(nuevoEmail, estudiante.getEmail());
    }

    @Test
    @DisplayName("Debería permitir modificar edad correctamente")
    void deberiaPermitirModificarEdad() {
        // ARRANGE
        int nuevaEdad = 26;
        // ACT
        estudiante.setEdad(nuevaEdad);
        // ASSERT
        assertEquals(nuevaEdad, estudiante.getEdad());
    }

    @Test
    @DisplayName("Debería permitir modificar curso correctamente")
    void deberiaPermitirModificarCurso() {
        // ARRANGE
        String nuevoCurso = "Java Avanzado";
        // ACT
        estudiante.setCurso(nuevoCurso);
        // ASSERT
        assertEquals(nuevoCurso, estudiante.getCurso());
    }

    @Test
    @DisplayName("Debería permitir asignar ID correctamente")
    void deberiaPermitirAsignarId() {
        // ARRANGE
        Long nuevoId = 5L;
        // ACT
        estudiante.setId(nuevoId);
        // ASSERT
        assertEquals(nuevoId, estudiante.getId());
    }

    @Test
    @DisplayName("Debería generar toString correctamente")
    void deberiaGenerarToStringCorrectamente() {
        // ARRANGE
        Estudiante estudiante = new Estudiante(1L, "Test User", "test@test.com", 21, "Testing");
        // ACT
        String resultado = estudiante.toString();
        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.contains("Test User"));
        assertTrue(resultado.contains("test@test.com"));
        assertTrue(resultado.contains("21"));
        assertTrue(resultado.contains("Testing"));
        assertTrue(resultado.contains("Estudiante{"));
    }

    @Test
    @DisplayName("Debería manejar estudiantes con ID null en equals - son iguales solo si son el mismo objeto")
    public void deberiaManejarIdNullEnEquals() {
        // Arrange
        Estudiante estudiante1 = new Estudiante("Sin ID", "sinid@test.com", 20, "Testing");
        Estudiante estudiante2 = new Estudiante("Sin ID 2", "sinid2@test.com", 22, "Testing");
        // Act & Assert
        assertNotEquals(estudiante1, estudiante2);
        // Pero un objeto debe ser igual a sí mismo
        assertEquals(estudiante1, estudiante1);
    }

    @Test
    @DisplayName("Debería modificar fecha de registro correctamente")
    public void deberiaModificarFechaRegistro() {
        // Arrange
        Estudiante estudiante = new Estudiante("Carlos López", "carlos@test.com", 25, "Python");
        LocalDateTime nuevaFecha = LocalDateTime.of(2023, 12, 1, 8, 0);
        // Act
        estudiante.setFechaRegistro(nuevaFecha);
        // Assert
        assertEquals(nuevaFecha, estudiante.getFechaRegistro());
    }

    @Test
    @DisplayName("Debería cambiar estado activo del estudiante")
    public void deberiaCambiarEstadoActivo() {
        // Arrange
        Estudiante estudiante = new Estudiante("María Rodríguez", "maria@test.com", 22, "JavaScript");
        assertTrue(estudiante.isActivo()); // Por defecto es true
        // Act
        estudiante.setActivo(false);
        // Assert
        assertFalse(estudiante.isActivo());
    }

    @Test
    @DisplayName("Debería ser igual a sí mismo (reflexividad)")
    public void deberiaSerIgualASiMismo() {
        // Arrange
        Estudiante estudiante = new Estudiante(1L, "Pedro Sánchez", "pedro@test.com", 24, "React");
        // Act & Assert
        assertEquals(estudiante, estudiante);
    }

    @Test
    @DisplayName("No debería ser igual a null")
    public void noDeberiaSerIgualANull() {
        // Arrange
        Estudiante estudiante = new Estudiante(1L, "Laura Martín", "laura@test.com", 23, "Angular");
        // Act & Assert
        assertNotEquals(estudiante, null);
    }

    @Test
    @DisplayName("No debería ser igual a objeto de otra clase")
    public void noDeberiaSerIgualAOtraClase() {
        // Arrange
        Estudiante estudiante = new Estudiante(1L, "Diego Torres", "diego@test.com", 26, "Vue");
        String otraClase = "No soy un estudiante";
        // Act & Assert
        assertNotEquals(estudiante, otraClase);
    }

    @Test
    @DisplayName("Debería ser igual a otro estudiante con mismo ID")
    public void deberiaSerIgualConMismoId() {
        // Arrange
        Estudiante estudiante1 = new Estudiante(1L, "Ana García", "ana@test.com", 20, "Java");
        Estudiante estudiante2 = new Estudiante(1L, "Pedro López", "pedro@test.com", 25, "Python");
        // Act & Assert
        assertEquals(estudiante1, estudiante2);
        assertEquals(estudiante1.hashCode(), estudiante2.hashCode());
    }

    @Test
    @DisplayName("No debería ser igual a estudiante con diferente ID")
    public void noDeberiaSerIgualConDiferenteId() {
        // Arrange
        Estudiante estudiante1 = new Estudiante(1L, "Ana García", "ana@test.com", 20, "Java");
        Estudiante estudiante2 = new Estudiante(2L, "Ana García", "ana@test.com", 20, "Java");
        // Act & Assert
        assertNotEquals(estudiante1, estudiante2);
    }
}
