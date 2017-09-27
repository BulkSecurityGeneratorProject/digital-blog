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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Capitulo capitulo = (Capitulo) o;
        if (capitulo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, capitulo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Capitulo{" +
            "id=" + id +
            ", numeroCapitulo='" + numeroCapitulo + "'" +
            '}';
    }
}
