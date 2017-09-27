package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RolePermiso.
 */
@Entity
@Table(name = "role_permiso")
public class RolePermiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Permiso idPermiso;

    @ManyToOne
    private Rol idRol;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permiso getIdPermiso() {
        return idPermiso;
    }

    public RolePermiso idPermiso(Permiso permiso) {
        this.idPermiso = permiso;
        return this;
    }

    public void setIdPermiso(Permiso permiso) {
        this.idPermiso = permiso;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public RolePermiso idRol(Rol rol) {
        this.idRol = rol;
        return this;
    }

    public void setIdRol(Rol rol) {
        this.idRol = rol;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RolePermiso rolePermiso = (RolePermiso) o;
        if (rolePermiso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rolePermiso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RolePermiso{" +
            "id=" + getId() +
            "}";
    }
}
