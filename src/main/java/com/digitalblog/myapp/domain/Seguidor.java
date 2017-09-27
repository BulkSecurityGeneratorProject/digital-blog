package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Seguidor.
 */
@Entity
@Table(name = "seguidor")
public class Seguidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado_seguidor")
    private Boolean estadoSeguidor;

    @ManyToOne
    private Usuario idSeguidor;

    @ManyToOne
    private Usuario idSeguido;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstadoSeguidor() {
        return estadoSeguidor;
    }

    public Seguidor estadoSeguidor(Boolean estadoSeguidor) {
        this.estadoSeguidor = estadoSeguidor;
        return this;
    }

    public void setEstadoSeguidor(Boolean estadoSeguidor) {
        this.estadoSeguidor = estadoSeguidor;
    }

    public Usuario getIdSeguidor() {
        return idSeguidor;
    }

    public Seguidor idSeguidor(Usuario usuario) {
        this.idSeguidor = usuario;
        return this;
    }

    public void setIdSeguidor(Usuario usuario) {
        this.idSeguidor = usuario;
    }

    public Usuario getIdSeguido() {
        return idSeguido;
    }

    public Seguidor idSeguido(Usuario usuario) {
        this.idSeguido = usuario;
        return this;
    }

    public void setIdSeguido(Usuario usuario) {
        this.idSeguido = usuario;
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
        Seguidor seguidor = (Seguidor) o;
        if (seguidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seguidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seguidor{" +
            "id=" + getId() +
            ", estadoSeguidor='" + isEstadoSeguidor() + "'" +
            "}";
    }
}
