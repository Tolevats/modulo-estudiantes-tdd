package com.educacion.dao;

import com.educacion.modelo.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class EstudianteDAOImpl implements EstudianteDAO {

    private final Supplier<Connection> connectionSupplier;

    public EstudianteDAOImpl(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Estudiante crear(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes(nombre, email, edad, curso) VALUES(?,?,?,?)";
        // The connection is NOT managed here. It will be managed by the caller.
        try (PreparedStatement pstmt = connectionSupplier.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getEmail());
            pstmt.setInt(3, estudiante.getEdad());
            pstmt.setString(4, estudiante.getCurso());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    estudiante.setId(generatedKeys.getLong(1));
                }
            }
            return estudiante;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating student: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Estudiante> obtenerPorId(Long id) {
        String sql = "SELECT * FROM estudiantes WHERE id = ? AND activo = TRUE";
        try (PreparedStatement pstmt = connectionSupplier.get().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToEstudiante(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student by id: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        String sql = "SELECT * FROM estudiantes WHERE activo = TRUE ORDER BY nombre";
        List<Estudiante> estudiantes = new ArrayList<>();
        try (Statement stmt = connectionSupplier.get().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                estudiantes.add(mapRowToEstudiante(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all students: " + e.getMessage(), e);
        }
        return estudiantes;
    }

    @Override
    public boolean actualizar(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre = ?, email = ?, edad = ?, curso = ? WHERE id = ? AND activo = TRUE";
        try (PreparedStatement pstmt = connectionSupplier.get().prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getEmail());
            pstmt.setInt(3, estudiante.getEdad());
            pstmt.setString(4, estudiante.getCurso());
            pstmt.setLong(5, estudiante.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String sql = "UPDATE estudiantes SET activo = FALSE WHERE id = ?";
        try (PreparedStatement pstmt = connectionSupplier.get().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existeEmail(String email) {
        String sql = "SELECT 1 FROM estudiantes WHERE email = ? AND activo = TRUE";
        try (PreparedStatement pstmt = connectionSupplier.get().prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking email existence: " + e.getMessage(), e);
        }
    }

    private Estudiante mapRowToEstudiante(ResultSet rs) throws SQLException {
        return new Estudiante(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getInt("edad"),
                rs.getString("curso")
        );
    }
}
