package com.digitalblog.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Seccion entity.
 */
public class SeccionDTO implements Serializable {

    private Long id;

    private String nombre;

    private String seccionName;

    private Long bibliotecaId;

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

    public Long getBibliotecaId() {
        return bibliotecaId;
    }

    public void setBibliotecaId(Long bibliotecaId) {
        this.bibliotecaId = bibliotecaId;
    }

    public String getSeccionName() {
        return seccionName;
    }

    public void setSeccionName(String seccionName) {
        this.seccionName = seccionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeccionDTO seccionDTO = (SeccionDTO) o;

        if ( ! Objects.equals(id, seccionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SeccionDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
