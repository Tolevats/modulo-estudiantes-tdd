package com.educacion.dao;

import com.educacion.modelo.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Data Access Object para Estudiante

public class EstudianteDAO {
    private final String dbUrl;

    public EstudianteDAO(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * Crear un nuevo estudiante en la base de datos
     */
    public Estudiante crear(Estudiante estudiante) throws SQLException {
        String sql = """
            INSERT INTO estudiantes (nombre, email, edad, curso, fecha_registro, activo)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getEmail());
            stmt.setInt(3, estudiante.getEdad());
            stmt.setString(4, estudiante.getCurso());
            stmt.setTimestamp(5, Timestamp.valueOf(estudiante.getFechaRegistro()));
            stmt.setBoolean(6, estudiante.isActivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se pudo crear el estudiante");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    estudiante.setId(generatedKeys.getLong(1));
                    return estudiante;
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }
        }
    }

    /**
     * Obtener un estudiante por su ID
     */
    public Optional<Estudiante> obtenerPorId(Long id) throws SQLException {
        String sql = """
            SELECT id, nombre, email, edad, curso, fecha_registro, activo 
            FROM estudiantes 
            WHERE id = ? AND activo = true
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearEstudiante(rs));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Obtener todos los estudiantes activos
     */
    public List<Estudiante> obtenerTodos() throws SQLException {
        String sql = """
            SELECT id, nombre, email, edad, curso, fecha_registro, activo 
            FROM estudiantes 
            WHERE activo = true 
            ORDER BY nombre
        """;

        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estudiantes.add(mapearEstudiante(rs));
            }
        }

        return estudiantes;
    }

    /**
     * Actualizar un estudiante existente
     */
    public boolean actualizar(Estudiante estudiante) throws SQLException {
        String sql = """
            UPDATE estudiantes 
            SET nombre = ?, email = ?, edad = ?, curso = ?
            WHERE id = ? AND activo = true
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getEmail());
            stmt.setInt(3, estudiante.getEdad());
            stmt.setString(4, estudiante.getCurso());
            stmt.setLong(5, estudiante.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Eliminar un estudiante (soft delete)
     */
    public boolean eliminar(Long id) throws SQLException {
        String sql = """
            UPDATE estudiantes 
            SET activo = false 
            WHERE id = ? AND activo = true
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Verificar si existe un email en la base de datos
     */
    public boolean existeEmail(String email) throws SQLException {
        String sql = """
            SELECT COUNT(*) 
            FROM estudiantes 
            WHERE email = ? AND activo = true
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    /**
     * Mapear ResultSet a objeto Estudiante
     */
    private Estudiante mapearEstudiante(ResultSet rs) throws SQLException {
        return new Estudiante(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getInt("edad"),
                rs.getString("curso"),
                rs.getTimestamp("fecha_registro").toLocalDateTime(),
                rs.getBoolean("activo")
        );
    }
}