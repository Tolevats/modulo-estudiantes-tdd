package com.educacion.modelo;

import java.util.Objects;

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

    // Constructor con parámetros
    public Estudiante(Long id, String nombre, String email, int edad, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.curso = curso;
    }

    // Constructor sin ID (para creación)
    public Estudiante(String nombre, String email, int edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estudiante that = (Estudiante) o;
        /*return edad == that.edad &&
                id != null ? id.equals(that.id) : that.id == null &&
                nombre != null ? nombre.equals(that.nombre) : that.nombre == null &&
                email != null ? email.equals(that.email) : that.email == null;*/
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        /*int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + edad;
        return result;*/
        return Objects.hash(id);
    }
}
