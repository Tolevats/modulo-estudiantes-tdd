package com.educacion.dao;

import com.educacion.modelo.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteDAO {
    private final String url;
    private final String usuario;
    private final String password;

    // Constructor para producción (base de datos real)
    public EstudianteDAO(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
    }

    // Constructor para SQLite (SQLonline)
    public EstudianteDAO(String urlSqlite) {
        this.url = urlSqlite;
        this.usuario = null;
        this.password = null;
    }

    /**
     * Obtener conexión a la base de datos
     */
    private Connection obtenerConexion() throws SQLException {
        if (usuario != null && password != null) {
            return DriverManager.getConnection(url, usuario, password);
        } else {
            // Para SQLite (SQLonline)
            return DriverManager.getConnection(url);
        }
    }

    /**
     * Crear un nuevo estudiante
     * OPERACIÓN CREATE del CRUD
     */
    public Estudiante crear(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiantes (nombre, email, edad, curso) VALUES (?, ?, ?, ?)";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getEmail());
            stmt.setInt(3, estudiante.getEdad());
            stmt.setString(4, estudiante.getCurso());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("Error al crear estudiante, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    estudiante.setId(generatedKeys.getLong(1));
                    return estudiante;
                } else {
                    throw new SQLException("Error al crear estudiante, no se obtuvo ID");
                }
            }
        }
    }

    /**
     * Obtener estudiante por ID
     * OPERACIÓN READ del CRUD
     */
    public Optional<Estudiante> obtenerPorId(Long id) throws SQLException {
        String sql = "SELECT id, nombre, email, edad, curso FROM estudiantes WHERE id = ? AND activo = TRUE";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Estudiante estudiante = new Estudiante(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getInt("edad"),
                            rs.getString("curso")
                    );
                    return Optional.of(estudiante);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Obtener todos los estudiantes activos
     * OPERACIÓN READ del CRUD
     */
    public List<Estudiante> obtenerTodos() throws SQLException {
        String sql = "SELECT id, nombre, email, edad, curso FROM estudiantes WHERE activo = TRUE ORDER BY nombre";
        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("edad"),
                        rs.getString("curso")
                );
                estudiantes.add(estudiante);
            }
        }

        return estudiantes;
    }

    /**
     * Verificar si existe un email
     */
    public boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE email = ? AND activo = TRUE";

        try (Connection conn = obtenerConexion();
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
     * Actualizar estudiante existente
     * OPERACIÓN UPDATE del CRUD
     */
    public boolean actualizar(Estudiante estudiante) throws SQLException {
        String sql = "UPDATE estudiantes SET nombre = ?, email = ?, edad = ?, curso = ? WHERE id = ? AND activo = TRUE";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getEmail());
            stmt.setInt(3, estudiante.getEdad());
            stmt.setString(4, estudiante.getCurso());
            stmt.setLong(5, estudiante.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Eliminar estudiante (soft delete)
     * OPERACIÓN DELETE del CRUD
     */
    public boolean eliminar(Long id) throws SQLException {
        String sql = "UPDATE estudiantes SET activo = FALSE WHERE id = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Eliminar físicamente (solo para pruebas)
     */
    public boolean eliminarFisicamente(Long id) throws SQLException {
        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }
}
