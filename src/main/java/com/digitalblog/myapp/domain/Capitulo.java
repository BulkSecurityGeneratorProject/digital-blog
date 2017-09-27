package com.digitalblog.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Capitulo.
 */
@Entity
@Table(name = "capitulo")
public class Capitulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_capitulo")
    private Integer numeroCapitulo;

    @ManyToOne
    private Publicacion capitulo;

    @ManyToOne
    private Publicacion idPublicacionC;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroCapitulo() {
        return numeroCapitulo;
    }

    public Capitulo numeroCapitulo(Integer numeroCapitulo) {
        this.numeroCapitulo = numeroCapitulo;
        return this;
    }

    public void setNumeroCapitulo(Integer numeroCapitulo) {
        this.numeroCapitulo = numeroCapitulo;
    }

    public Publicacion getCapitulo() {
        return capitulo;
    }

    public Capitulo capitulo(Publicacion publicacion) {
        this.capitulo = publicacion;
        return this;
    }

    public void setCapitulo(Publicacion publicacion) {
        this.capitulo = publicacion;
    }

    public Publicacion getIdPublicacionC() {
        return idPublicacionC;
    }

    public Capitulo idPublicacionC(Publicacion publicacion) {
        this.idPublicacionC = publicacion;
        return this;
    }

    public void setIdPublicacionC(Publicacion publicacion) {
        this.idPublicacionC = publicacion;
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
        Capitulo capitulo = (Capitulo) o;
        if (capitulo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capitulo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Capitulo{" +
            "id=" + getId() +
            ", numeroCapitulo='" + getNumeroCapitulo() + "'" +
            "}";
    }
}
