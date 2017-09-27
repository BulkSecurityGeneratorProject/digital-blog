package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Coolaborador.
 */
@Entity
@Table(name = "coolaborador")
public class Coolaborador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Capitulo capituloC;

    @ManyToOne
    private Publicacion publicacion;

    @ManyToOne
    private Usuario idUsuario;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Capitulo getCapituloC() {
        return capituloC;
    }

    public Coolaborador capituloC(Capitulo capitulo) {
        this.capituloC = capitulo;
        return this;
    }

    public void setCapituloC(Capitulo capitulo) {
        this.capituloC = capitulo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public Coolaborador publicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
        return this;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public Coolaborador idUsuario(Usuario usuario) {
        this.idUsuario = usuario;
        return this;
    }

    public void setIdUsuario(Usuario usuario) {
        this.idUsuario = usuario;
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
        Coolaborador coolaborador = (Coolaborador) o;
        if (coolaborador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coolaborador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coolaborador{" +
            "id=" + getId() +
            "}";
    }
}
