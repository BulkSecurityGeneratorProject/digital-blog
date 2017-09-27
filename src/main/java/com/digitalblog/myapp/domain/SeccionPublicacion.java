package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SeccionPublicacion.
 */
@Entity
@Table(name = "seccion_publicacion")
public class SeccionPublicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Seccion idSeccionSP;

    @ManyToOne
    private Publicacion idPublicacionSP;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seccion getIdSeccionSP() {
        return idSeccionSP;
    }

    public SeccionPublicacion idSeccionSP(Seccion seccion) {
        this.idSeccionSP = seccion;
        return this;
    }

    public void setIdSeccionSP(Seccion seccion) {
        this.idSeccionSP = seccion;
    }

    public Publicacion getIdPublicacionSP() {
        return idPublicacionSP;
    }

    public SeccionPublicacion idPublicacionSP(Publicacion publicacion) {
        this.idPublicacionSP = publicacion;
        return this;
    }

    public void setIdPublicacionSP(Publicacion publicacion) {
        this.idPublicacionSP = publicacion;
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
        SeccionPublicacion seccionPublicacion = (SeccionPublicacion) o;
        if (seccionPublicacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seccionPublicacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeccionPublicacion{" +
            "id=" + getId() +
            "}";
    }
}
