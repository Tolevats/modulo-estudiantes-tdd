package com.educacion.modelo;

import java.util.Objects;
import java.time.LocalDateTime;

/**
 * Clase Modelo que representa a un Estudiante.
 * Un POJO (Plain Old Java Object) que contiene los datos.
 */

public class Estudiante {
    private Long id;
    private String nombre;
    private String email;
    private int edad;
    private String curso;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    // Constructor con ID
    public Estudiante(Long id, String nombre, String email, int edad, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.curso = curso;
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    // Constructor sin ID (para nuevos estudiantes)
    public Estudiante(String nombre, String email, int edad, String curso) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.curso = curso;
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    // Constructor completo
    public Estudiante(Long id, String nombre, String email, int edad, String curso,
                      LocalDateTime fechaRegistro, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.curso = curso;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Buena práctica: sobreescribir equals() y hashCode()
    // (para que dos objetos Estudiante se consideren iguales si tienen el mismo id)
    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                ", curso='" + curso + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante that = (Estudiante) o;
        // Si ambos IDs son nulos, se consideran diferentes. Si uno es nulo y el otro no, también.
        // Solo son iguales si ambos IDs son no nulos y tienen el mismo valor.
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
