package com.educacion.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Estudiante
 * Lección 4 - TDD: Pruebas Unitarias y Mocking
 */
@DisplayName("Pruebas del Modelo Estudiante")
class EstudianteTest {

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada prueba
        estudiante = new Estudiante("Juan Pérez", "juan.perez@email.com", 25, "Java Básico");
    }

    @Test
    @DisplayName("Debería crear estudiante con constructor completo")
    void deberiaCrearEstudianteConConstructorCompleto() {
        // ARRANGE
        String nombre = "Ana García";
        String email = "ana.garcia@email.com";
        int edad = 22;
        String curso = "Testing Automation";

        // ACT
        Estudiante nuevoEstudiante = new Estudiante(nombre, email, edad, curso);

        // ASSERT
        assertNotNull(nuevoEstudiante);
        assertEquals(nombre, nuevoEstudiante.getNombre());
        assertEquals(email, nuevoEstudiante.getEmail());
        assertEquals(edad, nuevoEstudiante.getEdad());
        assertEquals(curso, nuevoEstudiante.getCurso());
        assertNull(nuevoEstudiante.getId()); // ID se asigna en BD
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
    @DisplayName("Debería generar toString con formato correcto")
    void deberiaGenerarToStringConFormatoCorrecto() {
        // ARRANGE
        estudiante.setId(1L);

        // ACT
        String resultado = estudiante.toString();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.contains("Juan Pérez"));
        assertTrue(resultado.contains("juan.perez@email.com"));
        assertTrue(resultado.contains("25"));
        assertTrue(resultado.contains("Java Básico"));
    }
}
