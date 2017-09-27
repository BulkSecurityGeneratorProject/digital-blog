package com.digitalblog.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Biblioteca.
 */
@Entity
@Table(name = "biblioteca")
public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_seccion")
    private Integer idSeccion;

    @Column(name = "id_jhi_user")
    private Long idJhiUser;

    @OneToOne
    @JoinColumn(unique = true)
    private Usuario idUsuarioB;

    @OneToMany(mappedBy = "biblioteca")
    @JsonIgnore
    private Set<Seccion> idSeccionBS = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public Biblioteca idSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
        return this;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Long getIdJhiUser() {
        return idJhiUser;
    }

    public Biblioteca idJhiUser(Long idJhiUser) {
        this.idJhiUser = idJhiUser;
        return this;
    }

    public void setIdJhiUser(Long idJhiUser) {
        this.idJhiUser = idJhiUser;
    }

    public Usuario getIdUsuarioB() {
        return idUsuarioB;
    }

    public Biblioteca idUsuarioB(Usuario usuario) {
        this.idUsuarioB = usuario;
        return this;
    }

    public void setIdUsuarioB(Usuario usuario) {
        this.idUsuarioB = usuario;
    }

    public Set<Seccion> getIdSeccionBS() {
        return idSeccionBS;
    }

    public Biblioteca idSeccionBS(Set<Seccion> seccions) {
        this.idSeccionBS = seccions;
        return this;
    }

    public Biblioteca addIdSeccionB(Seccion seccion) {
        this.idSeccionBS.add(seccion);
        seccion.setBiblioteca(this);
        return this;
    }

    public Biblioteca removeIdSeccionB(Seccion seccion) {
        this.idSeccionBS.remove(seccion);
        seccion.setBiblioteca(null);
        return this;
    }

    public void setIdSeccionBS(Set<Seccion> seccions) {
        this.idSeccionBS = seccions;
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
        Biblioteca biblioteca = (Biblioteca) o;
        if (biblioteca.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biblioteca.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
            "id=" + getId() +
            ", idSeccion='" + getIdSeccion() + "'" +
            ", idJhiUser='" + getIdJhiUser() + "'" +
            "}";
    }
}
