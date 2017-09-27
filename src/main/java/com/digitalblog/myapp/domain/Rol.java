package com.digitalblog.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Rol.
 */
@Entity
@Table(name = "rol")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "rol")
    @JsonIgnore
    private Set<Usuario> idUsuarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Rol descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Usuario> getIdUsuarios() {
        return idUsuarios;
    }

    public Rol idUsuarios(Set<Usuario> usuarios) {
        this.idUsuarios = usuarios;
        return this;
    }

    public Rol addIdUsuario(Usuario usuario) {
        this.idUsuarios.add(usuario);
        usuario.setRol(this);
        return this;
    }

    public Rol removeIdUsuario(Usuario usuario) {
        this.idUsuarios.remove(usuario);
        usuario.setRol(null);
        return this;
    }

    public void setIdUsuarios(Set<Usuario> usuarios) {
        this.idUsuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rol rol = (Rol) o;
        if (rol.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rol.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rol{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
