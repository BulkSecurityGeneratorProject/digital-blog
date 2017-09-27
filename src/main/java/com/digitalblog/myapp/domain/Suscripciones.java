package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Suscripciones.
 */
@Entity
@Table(name = "suscripciones")
public class Suscripciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_canal")
    private Integer idCanal;

    @Column(name = "id_siguiendo")
    private Integer idSiguiendo;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCanal() {
        return idCanal;
    }

    public Suscripciones idCanal(Integer idCanal) {
        this.idCanal = idCanal;
        return this;
    }

    public void setIdCanal(Integer idCanal) {
        this.idCanal = idCanal;
    }

    public Integer getIdSiguiendo() {
        return idSiguiendo;
    }

    public Suscripciones idSiguiendo(Integer idSiguiendo) {
        this.idSiguiendo = idSiguiendo;
        return this;
    }

    public void setIdSiguiendo(Integer idSiguiendo) {
        this.idSiguiendo = idSiguiendo;
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
        Suscripciones suscripciones = (Suscripciones) o;
        if (suscripciones.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suscripciones.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Suscripciones{" +
            "id=" + getId() +
            ", idCanal='" + getIdCanal() + "'" +
            ", idSiguiendo='" + getIdSiguiendo() + "'" +
            "}";
    }
}
